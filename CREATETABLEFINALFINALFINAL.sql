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
	avatar NVARCHAR(255),    
	onlineStatus BIT DEFAULT 1,
	created_at DATETIME DEFAULT GETDATE(),
	Certi nvarchar(250),
	Descrip nvarchar(500),
    SchoolID INT NULL,
	roleID int not null,
	isHot bit null,
	ParentEmail nvarchar(50),
	ParentPhone nvarchar(11),	  
    FOREIGN KEY (SchoolID) REFERENCES School(SchoolID),
	FOREIGN KEY (roleID) REFERENCES roles(roleID)
);
alter table [user]
add SchoolClassID int null
alter table [user]
add constraint FK_Students_Classes 
FOREIGN KEY (SchoolClassID) REFERENCES SchoolClass(SchoolClassID);
select * from [user]
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
	isActive int default 0
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
	isActive int default 0,
	createAt datetime default getdate()
    FOREIGN KEY (TutoringClassID) REFERENCES TutoringClass(TutoringClassID),
    FOREIGN KEY (TeacherID) REFERENCES [User](UserID)
);
alter table ClassGroup 
add minStudent int

alter table ClassGroup 
add MaxStudent int

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
	PDFPath NVARCHAR(255),
	ClassifyID INT,
    FOREIGN KEY (UploadedBy) REFERENCES [User](UserID),
	FOREIGN KEY (SubjectID) REFERENCES Subjects(SubjectID),
	FOREIGN KEY (GradeID) REFERENCES Grade(GradeID),
	FOREIGN KEY (ClassifyID) REFERENCES Classify(ClassifyID)
);

create table Classify(
	ClassifyID INT PRIMARY KEY IDENTITY,
	TypeDoc nvarchar(255)
);

-- 14. Thông báo (Notification)
CREATE TABLE Notifications (
    NotificationID INT PRIMARY KEY IDENTITY,
    Title NVARCHAR(255),
    Content NVARCHAR(MAX),
    CreatedAt DATETIME DEFAULT GETDATE(),
    TargetRole int, 
	CreatedBy int,
	isImportant bit,
	isRead bit default 0
	TargetRole INT
	FOREIGN KEY (CreatedBy) REFERENCES [User](UserID),
	FOREIGN KEY (TargetRole) REFERENCES roles(roleID)
);

-- 15. Thanh toán (Payment)
CREATE TABLE Payment (
    PaymentID INT PRIMARY KEY IDENTITY,
    UserID INT,
    TutoringClassID INT,
    Amount MONEY,
    PaymentDate DATE DEFAULT GETDATE(),
	isPaid bit default 0
    FOREIGN KEY (UserID) REFERENCES [User](UserID),
    FOREIGN KEY (TutoringClassID) REFERENCES TutoringClass(TutoringClassID)
);

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
	UserIntro INT NULL,
	Confirmed BIT DEFAULT 0,
	InterestCourses nvarchar(255)
	FOREIGN KEY (UserIntro) REFERENCES [User](UserID)
);


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
create table RequestActive(
	raID INT PRIMARY KEY IDENTITY,
	NameStudent nvarchar(100),
	email nvarchar(50),
	Birth DATE,
	School nvarchar(100),
	Class nvarchar(30),
	stt NVARCHAR(20) DEFAULT 'Pending',
	DateRequest DATETIME DEFAULT GETDATE()
);
