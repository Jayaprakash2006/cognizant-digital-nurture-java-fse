-- =============================================================
-- Exercise 3 - Stored Procedures
-- Scenario 2: UpdateEmployeeBonus
--             Adds a bonus percentage to the salary of every
--             employee in a given department.
--
-- Parameters:
--   p_department_id  - The target department ID
--   p_bonus_percent  - Bonus percentage to add (e.g., 10 = 10%)
-- =============================================================

CREATE OR REPLACE PROCEDURE UpdateEmployeeBonus (
    p_department_id IN Employees.DepartmentID%TYPE,
    p_bonus_percent IN NUMBER
)
AS
    v_bonus_amount  NUMBER;
    v_new_salary    NUMBER;
    v_count         NUMBER := 0;

    -- Cursor for all employees in the specified department
    CURSOR c_employees IS
        SELECT EmployeeID,
               Name,
               Salary
        FROM   Employees
        WHERE  DepartmentID = p_department_id;
BEGIN
    -- Validate bonus percentage
    IF p_bonus_percent <= 0 THEN
        RAISE_APPLICATION_ERROR(-20001, 'Bonus percentage must be greater than 0.');
    END IF;

    FOR rec IN c_employees LOOP
        v_bonus_amount := rec.Salary * (p_bonus_percent / 100);
        v_new_salary   := rec.Salary + v_bonus_amount;

        UPDATE Employees
        SET    Salary = v_new_salary
        WHERE  EmployeeID = rec.EmployeeID;

        v_count := v_count + 1;

        DBMS_OUTPUT.PUT_LINE(
            'Employee: '       || rec.Name        ||
            ' (ID: '           || rec.EmployeeID  || ')' ||
            ' | Old Salary: $' || TO_CHAR(rec.Salary,      'FM999,999,990.00') ||
            ' | Bonus ('       || p_bonus_percent  || '%): $' ||
                                  TO_CHAR(v_bonus_amount,  'FM999,999,990.00') ||
            ' | New Salary: $' || TO_CHAR(v_new_salary,    'FM999,999,990.00')
        );
    END LOOP;

    IF v_count = 0 THEN
        DBMS_OUTPUT.PUT_LINE(
            'No employees found in Department ID: ' || p_department_id
        );
    ELSE
        COMMIT;
        DBMS_OUTPUT.PUT_LINE(
            'Bonus of ' || p_bonus_percent || '% applied to ' ||
            v_count || ' employee(s) in Department ' || p_department_id || '.'
        );
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error in UpdateEmployeeBonus: ' || SQLERRM);
        RAISE;
END UpdateEmployeeBonus;
/

-- ---------------------------------------------------------------
-- Execute the procedure (example: 10% bonus for Department 3)
-- ---------------------------------------------------------------
BEGIN
    UpdateEmployeeBonus(
        p_department_id => 3,
        p_bonus_percent => 10
    );
END;
/
