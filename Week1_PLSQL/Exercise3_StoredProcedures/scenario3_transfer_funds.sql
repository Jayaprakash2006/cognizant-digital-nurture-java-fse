
CREATE OR REPLACE PROCEDURE TransferFunds (
    p_from_account_id IN  Accounts.AccountID%TYPE,
    p_to_account_id   IN  Accounts.AccountID%TYPE,
    p_amount          IN  NUMBER
)
AS
    v_from_balance  Accounts.Balance%TYPE;
    v_to_balance    Accounts.Balance%TYPE;
BEGIN

    IF p_amount <= 0 THEN
        RAISE_APPLICATION_ERROR(-20001, 'Transfer amount must be greater than zero.');
    END IF;

 
    BEGIN
        SELECT Balance
        INTO   v_from_balance
        FROM   Accounts
        WHERE  AccountID = p_from_account_id
        FOR UPDATE;             
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RAISE_APPLICATION_ERROR(
                -20002,
                'Source account ID ' || p_from_account_id || ' not found.'
            );
    END;


    IF v_from_balance < p_amount THEN
        RAISE_APPLICATION_ERROR(
            -20003,
            'Insufficient balance. Available: $' ||
            TO_CHAR(v_from_balance, 'FM999,999,990.00') ||
            ', Requested: $' ||
            TO_CHAR(p_amount, 'FM999,999,990.00')
        );
    END IF;


    BEGIN
        SELECT Balance
        INTO   v_to_balance
        FROM   Accounts
        WHERE  AccountID = p_to_account_id
        FOR UPDATE;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RAISE_APPLICATION_ERROR(
                -20004,
                'Destination account ID ' || p_to_account_id || ' not found.'
            );
    END;


    UPDATE Accounts
    SET    Balance = Balance - p_amount
    WHERE  AccountID = p_from_account_id;

    UPDATE Accounts
    SET    Balance = Balance + p_amount
    WHERE  AccountID = p_to_account_id;

    COMMIT;


    DBMS_OUTPUT.PUT_LINE('Transfer successful:');
    DBMS_OUTPUT.PUT_LINE(
        '  From Account: ' || p_from_account_id  ||
        ' | Previous Balance: $' || TO_CHAR(v_from_balance, 'FM999,999,990.00') ||
        ' | New Balance: $'      || TO_CHAR(v_from_balance - p_amount, 'FM999,999,990.00')
    );
    DBMS_OUTPUT.PUT_LINE(
        '  To   Account: ' || p_to_account_id    ||
        ' | Previous Balance: $' || TO_CHAR(v_to_balance, 'FM999,999,990.00') ||
        ' | New Balance: $'      || TO_CHAR(v_to_balance + p_amount, 'FM999,999,990.00')
    );
    DBMS_OUTPUT.PUT_LINE(
        '  Amount Transferred: $' || TO_CHAR(p_amount, 'FM999,999,990.00')
    );

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error in TransferFunds: ' || SQLERRM);
        RAISE;
END TransferFunds;
/

BEGIN
    TransferFunds(
        p_from_account_id => 1,
        p_to_account_id   => 2,
        p_amount          => 500
    );
END;
/
