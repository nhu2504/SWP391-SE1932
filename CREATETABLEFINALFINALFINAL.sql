create database eduraFINALFINALFINAL
use eduraFINALFINALFINAL

--1.Roles 1-admin 2-teacher 3-student 4-manager
CREATE TABLE roles(
	roleID INT PRIMARY KEY IDENTITY,
	roleName NVARCHAR(30) not null
);
select roleName from roles where roleID = 2
-- 2. Trường học (School)
CREATE TABLE School (
    SchoolID INT PRIMARY KEY IDENTITY,
    SchoolName NVARCHAR(50) NOT NULL,
	AddressSchool NVARCHAR(100) NOT NULL
);
--new
--3.Khối lớp 
CREATE TABLE Grade(
	GradeID INT PRIMARY KEY IDENTITY,
	GradeName NVARCHAR(10) not null
)
select * from Document
select * from [user]
select * from TeacherClass where userID = 2
select * from TeacherSubjects where userID = 2
-- 4. Lớp học trong trường (SchoolClass)
CREATE TABLE SchoolClass (
    SchoolClassID INT PRIMARY KEY IDENTITY,
    ClassName NVARCHAR(20),
    SchoolID INT,
	GradeID INT,
    FOREIGN KEY (SchoolID) REFERENCES School(SchoolID),
	FOREIGN KEY (GradeID) REFERENCES Grade(GradeID)
);
-- 5. Người dùng 1-admin 2-teacher 3-student 4-manager
CREATE TABLE [User] (
    UserID INT PRIMARY KEY IDENTITY,
    FullName NVARCHAR(100) NOT NULL,
    Gender NVARCHAR(10),
    BirthDate DATE,
    phone NVARCHAR(20),
    email NVARCHAR(50),
    pass NVARCHAR(50),
	avatar NVARCHAR(50),    
	onlineStatus BIT DEFAULT 1,
	created_at DATETIME DEFAULT GETDATE(),
	Certi nvarchar(250),
	Descrip nvarchar(500),
    SchoolID INT NULL,
    SchoolClassID INT NULL,
	roleID int not null,
	isHot bit null,
	ParentEmail nvarchar(50),
	ParentPhone nvarchar(11),	  
    FOREIGN KEY (SchoolID) REFERENCES School(SchoolID),
    FOREIGN KEY (SchoolClassID) REFERENCES SchoolClass(SchoolClassID),
	FOREIGN KEY (roleID) REFERENCES roles(roleID)
);
ALTER TABLE [User] DROP CONSTRAINT FK__User__SchoolClas__440B1D61;
ALTER TABLE [User]
ALTER COLUMN avatar NVARCHAR(255);


ALTER TABLE [User] DROP COLUMN SchoolClassID;
CREATE TABLE TeacherClass (
    UserID INT NOT NULL,
    SchoolClassID INT NOT NULL,
	PRIMARY KEY (UserID, SchoolClassID), 
    FOREIGN KEY (UserID) REFERENCES [User](UserID),
    FOREIGN KEY (SchoolClassID) REFERENCES SchoolClass(SchoolClassID)
);

CREATE TABLE TokenForgetPassword(
	TokenID INT PRIMARY KEY IDENTITY,
	token nvarchar(255) not null,
	expTime DATETIME DEFAULT GETDATE(),
	isUsed bit not null,
	userID int not null,
	FOREIGN KEY (userID) REFERENCES [User](UserID),
);
ALTER TABLE TokenForgetPassword DROP COLUMN expTime;
ALTER TABLE TokenForgetPassword ADD expTime DATETIME DEFAULT GETDATE();


-- 6. Môn học (Subject)(Toán,Văn,Anh,Sử,Địa,Lí,Hóa,Sinh)
CREATE TABLE Subjects (
    SubjectID INT PRIMARY KEY IDENTITY,
    SubjectName NVARCHAR(50) NOT NULL,
	ImageSubject nvarchar(50)
);
CREATE TABLE TeacherSubjects (
    UserID INT NOT NULL,
    SubjectID INT NOT NULL,
    PRIMARY KEY (UserID, SubjectID),
    FOREIGN KEY (UserID) REFERENCES [User](UserID),
    FOREIGN KEY (SubjectID) REFERENCES Subjects(SubjectID)
);


--7.Ca hoc
CREATE TABLE Shiftlearn(
	ShiftID int primary key identity,
	Start_time time not null,
	End_time time not null
);

select *from Schedule where userID = 3 and datelearn = '2025-06-25'
select*from TutoringRegistrationPending
--8.Room
CREATE TABLE Room (
    id INT PRIMARY KEY IDENTITY,
    roomName VARCHAR(50) NOT NULL
);
-- 9. Khóa học(TutoringClass)
CREATE TABLE TutoringClass (
    TutoringClassID INT PRIMARY KEY IDENTITY,
    ClassName NVARCHAR(100),
	ImageTutoring nvarchar(50),
	Descrip nvarchar(200),
	isHot bit,
    SubjectID INT,
    StartDate DATE,
    EndDate DATE,
	Tuitionfee money,
	GradeID INT,
    FOREIGN KEY (SubjectID) REFERENCES Subjects(SubjectID),
	FOREIGN KEY (GradeID) REFERENCES Grade(GradeID)
);
--10.Lớp học của các khóa học
CREATE TABLE ClassGroup (
    ClassGroupID INT PRIMARY KEY IDENTITY,
    TutoringClassID INT NOT NULL,
    ClassGroupName NVARCHAR(100), 
    MaxStudent INT DEFAULT 30 CHECK (MaxStudent BETWEEN 1 AND 30),
    TeacherID INT,
    FOREIGN KEY (TutoringClassID) REFERENCES TutoringClass(TutoringClassID),
    FOREIGN KEY (TeacherID) REFERENCES [User](UserID)
);

-- 11. Điểm danh của lớp (Attendance)
CREATE TABLE Attendance (
    ClassGroupID INT,
    UserID INT,
    AttendanceDate DATETIME DEFAULT GETDATE() ,
    IsPresent BIT,
	primary key(ClassGroupID,UserID,AttendanceDate),
    FOREIGN KEY (ClassGroupID) REFERENCES ClassGroup(ClassGroupID),
    FOREIGN KEY (UserID) REFERENCES [User](UserID)
);

-- 12. Bình luận (Comment)
CREATE TABLE Comment (
    CommentID INT PRIMARY KEY IDENTITY,
    TutoringClassID INT,
    UserID INT,
    CommentText NVARCHAR(MAX),
    CommentDate DATETIME DEFAULT GETDATE(),
	Rating INT CHECK (Rating >= 1 AND Rating <= 5),
    FOREIGN KEY (TutoringClassID) REFERENCES TutoringClass(TutoringClassID),
    FOREIGN KEY (UserID) REFERENCES [User](UserID)
);


-- 13. Tài liệu (Document)
CREATE TABLE Document (
    DocumentID INT PRIMARY KEY IDENTITY,
    Title NVARCHAR(100) NOT NULL,
	Descrip nvarchar(MAX),
    UploadedBy INT,                 
    UploadDate DATETIME DEFAULT GETDATE(),           
	SubjectID INT,
	GradeID INT,
    FOREIGN KEY (UploadedBy) REFERENCES [User](UserID),
	FOREIGN KEY (SubjectID) REFERENCES Subjects(SubjectID),
	FOREIGN KEY (GradeID) REFERENCES Grade(GradeID)
);
ALTER TABLE Document
ADD ClassifyID INT;

ALTER TABLE Document
ADD CONSTRAINT FK_Document_Classify
FOREIGN KEY (ClassifyID) REFERENCES Classify(ClassifyID);

create table Classify(
	ClassifyID INT PRIMARY KEY IDENTITY,
	TypeDoc nvarchar(255)
);
alter table Document
add PDFPath NVARCHAR(255)

-- 14. Thông báo (Notification)
CREATE TABLE Notifications (
    NotificationID INT PRIMARY KEY IDENTITY,
    Title NVARCHAR(255),
    Content NVARCHAR(MAX),
    CreatedAt DATETIME DEFAULT GETDATE(),
    TargetRole int, 
	CreatedBy int
	FOREIGN KEY (CreatedBy) REFERENCES [User](UserID),
	FOREIGN KEY (TargetRole) REFERENCES roles(roleID)
);
alter table Notifications add isRead bit default 0;
alter table Notifications add isImportant bit ;

-- 15. Thanh toán (Payment)
CREATE TABLE Payment (
    PaymentID INT PRIMARY KEY IDENTITY,
    UserID INT,
    TutoringClassID INT,
    Amount MONEY,
    PaymentDate DATE DEFAULT GETDATE(),
    FOREIGN KEY (UserID) REFERENCES [User](UserID),
    FOREIGN KEY (TutoringClassID) REFERENCES TutoringClass(TutoringClassID)
);
alter table Payment add isPaid bit default 0;

--16. setting

CREATE TABLE CenterInfo (
    CenterID INT PRIMARY KEY, -- Luôn là 1
    NameCenter NVARCHAR(255) NOT NULL,
    DescripCenter NVARCHAR(MAX),
	DescripCenter1 NVARCHAR(MAX),
    AddressCenter NVARCHAR(255),
	Logo nvarchar(50),
	imageCenter nvarchar(50),
	Phone NVARCHAR(20),
    Email NVARCHAR(255),
    Website NVARCHAR(255),
    Facebook NVARCHAR(255),
    Zalo NVARCHAR(255),
    Youtube NVARCHAR(255),
    Tiktok NVARCHAR(255),
    LastUpdated DATETIME DEFAULT GETDATE(),
	UpdatedBy int,
	YearOfWork int,
    FOREIGN KEY (UpdatedBy) REFERENCES [User](UserID)
);

--17.
create table banner(
	bannerID int primary key identity,
	bannerImg nvarchar(50),
	CenterID INT,
	FOREIGN KEY (CenterID) REFERENCES CenterInfo(CenterID)
);
select * from TutoringRegistrationPending
select * from [user]
--18.Đăng ký dành cho người chưa có tài khoản
CREATE TABLE TutoringRegistrationPending (
    RegistrationPendingID INT PRIMARY KEY IDENTITY,
    FullName NVARCHAR(255),
    Phone NVARCHAR(20),
    Email NVARCHAR(255),
    RegisterDate DATETIME DEFAULT GETDATE(),
    ApprovalStatus NVARCHAR(20) DEFAULT 'Pending',
	Gender NVARCHAR(10),
    BirthDate DATE,
    School NVARCHAR(255),
	AddressSchool NVARCHAR(255),
    Class NVARCHAR(50),
    ParentPhone NVARCHAR(20),
    ParentEmail NVARCHAR(255),
	
	Confirmed BIT DEFAULT 0,
	
);
-- Bước 1: Cho phép NULL
ALTER TABLE TutoringRegistrationPending
ALTER COLUMN UserIntro INT NULL;

-- Bước 2: Thêm FOREIGN KEY nếu chưa có
ALTER TABLE TutoringRegistrationPending
ADD CONSTRAINT FK_UserIntro FOREIGN KEY (UserIntro) REFERENCES [User](UserID);
alter table TutoringRegistrationPending
add InterestCourses nvarchar(255)

--19.Bảng lịch học và lịch dạy
CREATE TABLE Schedule (
    ScheduleID INT PRIMARY KEY IDENTITY,
    ClassGroupID INT NOT NULL,   -- Lớp học
    ShiftID INT NOT NULL,            -- Ca học
    RoomID INT NOT NULL,             -- Phòng học
    DateLearn DATE NOT NULL,              -- Ngày dạy/học
    UserID INT NOT NULL,             -- Giáo viên 
    FOREIGN KEY (ClassGroupID) REFERENCES ClassGroup(ClassGroupID),
    FOREIGN KEY (ShiftID) REFERENCES Shiftlearn(ShiftID),
    FOREIGN KEY (RoomID) REFERENCES Room(id),
    FOREIGN KEY (UserID) REFERENCES [User](UserID)
);


--20
CREATE TABLE ScheduleTemplate (
    TemplateID INT PRIMARY KEY IDENTITY,
    ClassGroupID INT NOT NULL,
    DayOfWeek INT NOT NULL, -- 0=Chủ Nhật, 1=Thứ Hai, ..., 6=Thứ Bảy
    ShiftID INT NOT NULL,
    RoomID INT NOT NULL,
    TeacherID INT NOT NULL, -- Giáo viên
    FOREIGN KEY (ClassGroupID) REFERENCES ClassGroup(ClassGroupID),
    FOREIGN KEY (ShiftID) REFERENCES Shiftlearn(ShiftID),
    FOREIGN KEY (RoomID) REFERENCES Room(ID),
    FOREIGN KEY (TeacherID) REFERENCES [User](UserID)
);

--21
CREATE TABLE ClassGroup_Student (
    CsID INT PRIMARY KEY IDENTITY,
    ClassGroupID INT NOT NULL,
    StudentID INT NOT NULL,
    EnrollDate DATE DEFAULT GETDATE(),
    IsActive BIT DEFAULT 1, -- 1: đang học, 0: đã rút
    FOREIGN KEY (ClassGroupID) REFERENCES ClassGroup(ClassGroupID),
    FOREIGN KEY (StudentID) REFERENCES [User](UserID)
);
--22
CREATE TABLE TutoringRegistration (
    RegistrationID INT PRIMARY KEY IDENTITY,
    UserID INT,
    TutoringClassID INT,
    RegisterDate DATETIME DEFAULT GETDATE() ,
    FOREIGN KEY (UserID) REFERENCES [User](UserID),
    FOREIGN KEY (TutoringClassID) REFERENCES TutoringClass(TutoringClassID)
);
select * from school
select * from SchoolClass
update [user]
set avatar= '/WebApplication3/image-loader/team-5.jpg'
where UserID = 6
select * from teacherclass
select * from [user]
update [user]
set SchoolID = 7
where UserID in (5,11,15,18,21)
update school set schoolname = N'Hà Thành' where schoolid = 1
update school set schoolname = N'Xuân Đỉnh' where schoolid = 2
update school set schoolname = N'Minh Khai' where schoolid = 3
update school set schoolname = N'Thượng Cát' where schoolid = 4
update school set schoolname = N'Tây Đô' where schoolid = 5

SELECT * 
FROM TutoringRegistrationPending
ORDER BY 
    CASE 
        WHEN ApprovalStatus = 'Pending' THEN 0 
        ELSE 1 
    END,
    RegisterDate ASC;
