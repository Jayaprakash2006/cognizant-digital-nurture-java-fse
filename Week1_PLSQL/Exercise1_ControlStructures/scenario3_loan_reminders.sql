-- =============================================================
-- Exercise 1 - Control Structures
-- Scenario 3: Print reminder messages for loans due within
--             the next 30 days.
-- =============================================================

DECLARE
    -- Cursor to fetch all loans due within the next 30 days
    CURSOR c_due_loans IS
        SELECT l.LoanID,
               l.DueDate,
               l.LoanAmount,
               c.CustomerID,
               c.Name        AS CustomerName,
               c.Email
        FROM   Loans     l
        JOIN   Customers c ON c.CustomerID = l.CustomerID
        WHERE  l.DueDate BETWEEN SYSDATE AND SYSDATE + 30
        ORDER  BY l.DueDate;

    v_days_remaining NUMBER;
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== Loan Due Reminders ===');
    DBMS_OUTPUT.PUT_LINE('Generated on: ' || TO_CHAR(SYSDATE, 'DD-MON-YYYY'));
    DBMS_OUTPUT.PUT_LINE('');

    FOR rec IN c_due_loans LOOP
        -- Calculate exact days remaining until due date
        v_days_remaining := TRUNC(rec.DueDate) - TRUNC(SYSDATE);

        DBMS_OUTPUT.PUT_LINE(
            'REMINDER >> Customer: '  || rec.CustomerName  ||
            ' (ID: '                  || rec.CustomerID    || ')' ||
            ' | Loan ID: '            || rec.LoanID        ||
            ' | Amount: $'            || TO_CHAR(rec.LoanAmount, 'FM999,999,990.00') ||
            ' | Due Date: '           || TO_CHAR(rec.DueDate, 'DD-MON-YYYY') ||
            ' | Days Remaining: '     || v_days_remaining
        );
    END LOOP;

    -- Inform if no loans are due in the window
    IF c_due_loans%ROWCOUNT = 0 THEN
        DBMS_OUTPUT.PUT_LINE('No loans are due within the next 30 days.');
    END IF;

    DBMS_OUTPUT.PUT_LINE('');
    DBMS_OUTPUT.PUT_LINE('=== End of Reminders ===');

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END;
/
