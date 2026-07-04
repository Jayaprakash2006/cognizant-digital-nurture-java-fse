-- =============================================================
-- Exercise 1 - Control Structures
-- Scenario 2: Promote customers to VIP status when their
--             balance exceeds $10,000.
-- =============================================================

DECLARE
    -- Cursor to iterate through all customers
    CURSOR c_customers IS
        SELECT CustomerID,
               Name,
               Balance,
               IsVIP
        FROM   Customers;
BEGIN
    FOR rec IN c_customers LOOP
        IF rec.Balance > 10000 THEN
            -- Set IsVIP flag to TRUE (stored as 'Y' in a CHAR column
            -- or 1 in a NUMBER column; adjust to your schema as needed)
            UPDATE Customers
            SET    IsVIP = 'Y'
            WHERE  CustomerID = rec.CustomerID;

            DBMS_OUTPUT.PUT_LINE(
                'Customer: ' || rec.Name ||
                ' | Balance: $' || TO_CHAR(rec.Balance, 'FM999,999,990.00') ||
                ' => Promoted to VIP.'
            );
        ELSE
            -- Ensure non-qualifying customers are not flagged
            UPDATE Customers
            SET    IsVIP = 'N'
            WHERE  CustomerID = rec.CustomerID;

            DBMS_OUTPUT.PUT_LINE(
                'Customer: ' || rec.Name ||
                ' | Balance: $' || TO_CHAR(rec.Balance, 'FM999,999,990.00') ||
                ' => Not eligible for VIP.'
            );
        END IF;
    END LOOP;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('VIP status update completed successfully.');

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END;
/
