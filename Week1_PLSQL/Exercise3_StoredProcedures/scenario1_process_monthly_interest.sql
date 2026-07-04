
CREATE OR REPLACE PROCEDURE ProcessMonthlyInterest
AS
    CURSOR c_savings IS
        SELECT AccountID,
               Balance
        FROM   Accounts
        WHERE  AccountType = 'Savings';

    v_interest      NUMBER;
    v_new_balance   NUMBER;
    v_count         NUMBER := 0;
BEGIN
    FOR rec IN c_savings LOOP
        v_interest    := rec.Balance * 0.01;
        v_new_balance := rec.Balance + v_interest;

        UPDATE Accounts
        SET    Balance = v_new_balance
        WHERE  AccountID = rec.AccountID;

        v_count := v_count + 1;

        DBMS_OUTPUT.PUT_LINE(
            'AccountID: '    || rec.AccountID  ||
            ' | Old Balance: $' || TO_CHAR(rec.Balance,   'FM999,999,990.00') ||
            ' | Interest: $'    || TO_CHAR(v_interest,    'FM999,999,990.00') ||
            ' | New Balance: $' || TO_CHAR(v_new_balance, 'FM999,999,990.00')
        );
    END LOOP;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE(
        'Monthly interest processed for ' || v_count || ' savings account(s).'
    );

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error in ProcessMonthlyInterest: ' || SQLERRM);
        RAISE;
END ProcessMonthlyInterest;
/

BEGIN
    ProcessMonthlyInterest;
END;
/
