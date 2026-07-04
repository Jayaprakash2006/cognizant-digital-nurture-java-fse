-- =============================================================
-- Exercise 1 - Control Structures
-- Scenario 1: Apply 1% discount to loan interest rates for
--             customers above 60 years old.
-- =============================================================

DECLARE
    -- Cursor to loop through all customers along with their loans
    CURSOR c_customers IS
        SELECT c.CustomerID,
               c.Name,
               TRUNC(MONTHS_BETWEEN(SYSDATE, c.DateOfBirth) / 12) AS Age,
               l.LoanID,
               l.InterestRate
        FROM   Customers c
        JOIN   Loans l ON l.CustomerID = c.CustomerID;

    v_new_rate Loans.InterestRate%TYPE;
BEGIN
    FOR rec IN c_customers LOOP
        IF rec.Age > 60 THEN
            -- Apply a 1% discount to the current interest rate
            v_new_rate := rec.InterestRate - 1;

            -- Ensure interest rate does not go below 0
            IF v_new_rate < 0 THEN
                v_new_rate := 0;
            END IF;

            UPDATE Loans
            SET    InterestRate = v_new_rate
            WHERE  LoanID = rec.LoanID;

            DBMS_OUTPUT.PUT_LINE(
                'Customer: ' || rec.Name ||
                ' (Age: '    || rec.Age  || ')' ||
                ' | LoanID: '|| rec.LoanID ||
                ' | Old Rate: ' || rec.InterestRate || '%' ||
                ' | New Rate: ' || v_new_rate       || '%'
            );
        END IF;
    END LOOP;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Loan interest rate discount applied successfully.');

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END;
/
