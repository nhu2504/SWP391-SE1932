--lay ra ten proc 
--SELECT name, create_date, modify_date 
--FROM sys.procedures;
--drop proc sp_CreateAccount


CREATE PROCEDURE sp_CreateAccount
    @regisID INT
AS
BEGIN
    -- 1. Cập nhật trạng thái duyệt
    UPDATE TutoringRegistrationPending
    SET ApprovalStatus = 'Accepted'
    WHERE RegistrationPendingID = @regisID;

    -- 2. Lấy thông tin từ bản đăng ký
    DECLARE @FullName NVARCHAR(255), @Email NVARCHAR(255), @Phone NVARCHAR(20), @Gender NVARCHAR(10),
            @BirthDate DATE, @RawSchool NVARCHAR(255), @AddressSchool NVARCHAR(255), @Class NVARCHAR(50),
            @ProcessedSchool NVARCHAR(255);

    SELECT @FullName = FullName, @Email = Email, @Phone = Phone, @Gender = Gender,
           @BirthDate = BirthDate, @RawSchool = School, @AddressSchool = AddressSchool, @Class = Class
    FROM TutoringRegistrationPending
    WHERE RegistrationPendingID = @regisID;

    -- 3. Cắt bỏ "Trường THPT " nếu có
    IF CHARINDEX(N'Trường THPT ', @RawSchool) > 0
        SET @ProcessedSchool = LTRIM(RTRIM(SUBSTRING(@RawSchool, CHARINDEX(N'Trường THPT ', @RawSchool) + LEN(N'Trường THPT '), LEN(@RawSchool))))
    ELSE
        SET @ProcessedSchool = @RawSchool;

    -- 4. Thêm trường nếu chưa có (không phân biệt hoa thường)
    IF NOT EXISTS (
        SELECT 1 FROM School 
        WHERE SchoolName COLLATE SQL_Latin1_General_CP1_CI_AS = @ProcessedSchool COLLATE SQL_Latin1_General_CP1_CI_AS
    )
    BEGIN
        INSERT INTO School (SchoolName, AddressSchool)
        VALUES (@ProcessedSchool, @AddressSchool);
    END

    DECLARE @SchoolID INT = (
        SELECT SchoolID FROM School 
        WHERE SchoolName COLLATE SQL_Latin1_General_CP1_CI_AS = @ProcessedSchool COLLATE SQL_Latin1_General_CP1_CI_AS
    );

    -- 5. Thêm lớp nếu chưa có
    IF NOT EXISTS (
        SELECT 1 FROM SchoolClass 
        WHERE ClassName COLLATE SQL_Latin1_General_CP1_CI_AS = @Class COLLATE SQL_Latin1_General_CP1_CI_AS
          AND SchoolID = @SchoolID
    )
    BEGIN
        INSERT INTO SchoolClass (ClassName, SchoolID)
        VALUES (@Class, @SchoolID);
    END

    DECLARE @ClassID INT = (
        SELECT SchoolClassID FROM SchoolClass 
        WHERE ClassName COLLATE SQL_Latin1_General_CP1_CI_AS = @Class COLLATE SQL_Latin1_General_CP1_CI_AS
          AND SchoolID = @SchoolID
    );

    -- 6. Thêm user mới, thêm mật khẩu mặc định và ảnh mặc định
    INSERT INTO [User] (
        FullName, Email, Phone, Gender, BirthDate,
        RoleID, SchoolID, SchoolClassID, Pass, Avatar
    )
    VALUES (
        @FullName, @Email, @Phone, @Gender, @BirthDate,
        3, @SchoolID, @ClassID, '1234', '/WebApplication3/image-loader/avatamacdinh.jpg'
    );
END

