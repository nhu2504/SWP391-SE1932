CREATE OR ALTER TRIGGER trg_InsertScheduleFromTemplate
ON ScheduleTemplate
AFTER INSERT
AS
BEGIN
    SET NOCOUNT ON;

    INSERT INTO Schedule (ClassGroupID, ShiftID, RoomID, DateLearn, UserID)
    SELECT 
        i.ClassGroupID,         -- tương ứng với GroupClassID mới trong bảng Schedule
        i.ShiftID,
        i.RoomID,
        d.DateLearn,
        i.TeacherID
    FROM inserted i
    JOIN ClassGroup cg ON i.ClassGroupID = cg.ClassGroupID
    JOIN TutoringClass tc ON cg.TutoringClassID = tc.TutoringClassID
    CROSS APPLY (
        SELECT DATEADD(DAY, n.number, tc.StartDate) AS DateLearn
        FROM master.dbo.spt_values n
        WHERE n.type = 'P'
          AND n.number BETWEEN 0 AND DATEDIFF(DAY, tc.StartDate, tc.EndDate)
          AND DATEPART(WEEKDAY, DATEADD(DAY, n.number, tc.StartDate)) = i.DayOfWeek
    ) AS d;
END;