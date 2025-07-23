
--1.Roles 1-admin 2-teacher 3-student 4-manager
insert into roles(roleName)
values
('admin'),
('teacher'),
('student'),
('manager');
-- 2. Trường học (School)
insert into School(SchoolName,AddressSchool)
values
(N'Trường THPT Hà Thành',N'Cổ Nhuế - Bắc Từ Liêm - Hà Nội'),
(N'Trường THPT Xuân Đỉnh',N'Xuân Đỉnh - Bắc Từ Liêm - Hà Nội'),
(N'Trường THPT Minh Khai',N'Phúc Diễn - Bắc Từ Liêm - Hà Nội'),
(N'Trường THPT Thượng Cát',N'Thượng Cát - Bắc Từ Liêm - Hà Nội'),
(N'Trường THPT Tây Đô',N'Phúc Lý - Bắc Từ Liêm - Hà Nội');

insert into School(SchoolName,AddressSchool)
values
(N'Tự Do',N'Tự Do');

select * from Schoolclass
--3.Khối lớp 
insert into Grade(GradeName)
values
(N'Khối 10'),
(N'Khối 11'),
(N'Khối 12');

-- 4. Lớp học trong trường (SchoolClass)
insert into SchoolClass(ClassName,SchoolID,GradeID)
values
(N'Lớp 10A',1,1),
(N'Lớp 10B',1,1),
(N'Lớp 10A',2,1),
(N'Lớp 10B',2,1),
(N'Lớp 10A',3,1),
(N'Lớp 10B',3,1),
(N'Lớp 10A',4,1),
(N'Lớp 10B',4,1),
(N'Lớp 10A',5,1),
(N'Lớp 10B',5,1),
(N'Lớp 11A',1,2),
(N'Lớp 11B',1,2),
(N'Lớp 11A',2,2),
(N'Lớp 11B',2,2),
(N'Lớp 11A',3,2),
(N'Lớp 11B',3,2),
(N'Lớp 11A',4,2),
(N'Lớp 11B',4,2),
(N'Lớp 11A',5,2),
(N'Lớp 11B',5,2),
(N'Lớp 12A',1,3),
(N'Lớp 12B',1,3),
(N'Lớp 12A',2,3),
(N'Lớp 12B',2,3),
(N'Lớp 12A',3,3),
(N'Lớp 12B',3,3),
(N'Lớp 12A',4,3),
(N'Lớp 12B',4,3),
(N'Lớp 12A',5,3),
(N'Lớp 12B',5,3);

insert into TeacherClass(UserID,SchoolClassID)
values
(2,1),
(2,2),
(2,12);
insert into TeacherClass(UserID,SchoolClassID)
values
(23,21);
-- 5. Người dùng 1-admin 2-teacher 3-student 4-manager
INSERT INTO [User] (
    FullName, Gender, BirthDate, phone, email, pass, avatar,
    Certi, Descrip, SchoolID, SchoolClassID, roleID, ParentEmail, ParentPhone,isHot
)

VALUES 
(N'Đỗ Ngọc Anh', N'Nữ', '2004-09-03', '0901548271', 'anhdnhe180661@fpt.edu.vn', 'ngocanh', 'admin.jpg',
    null, null, null, NULL, 1, 
    NULL, NULL,null
),
( N'Đặng Tú Anh', N'Nữ', '1992-08-15', '0901234567', 'tuanh@gmail.com', 'tuanh', 'team-1.jpg',
    N'Bằng thạc sĩ Đại học Sư Phạm', N'Cô Đặng Tú Anh - Đại học Sư phạm Hà Nội, 5 năm giảng dạy THPT, chuyên luyện thi tốt nghiệp và đại học. Từng hướng dẫn học sinh đạt giải học sinh giỏi Toán cấp thành phố. Dạy kỹ, chắc căn bản, giỏi truyền cảm hứng, được học sinh tín nhiệm vì dễ hiểu và tận tình.', 
	1, 1, 2, NULL, NULL,1),
( N'Phạm Hữu Cường', N'Nam', '1981-11-25', '0946748512', 'chuchegirl9@gmail.com', 'huucuong', 'team-2.jpg',
    N'Thạc sĩ Khoa học Tự nhiên Hà Nội', N'Thầy Phạm Hữu Cường - Thạc sĩ Giáo dục học, từng tu nghiệp ngắn hạn tại Singapore. Có kinh nghiệm giảng dạy và luyện thi tốt nghiệp THPT nhiều năm. Phong cách dạy gần gũi, dễ hiểu, luôn theo sát tiến độ học tập và tâm lý học sinh.', 
	1, 2, 2, NULL, NULL,1),
( N'Cao Huyền Dương', N'Nữ', '1983-02-03', '0983614384', 'huyenduong@gmail.com', 'huyenduong', 'team-3.jpg',
    N'Cử nhân Sư Phạm', N'Cô Nguyễn Huyền Dương - Cử nhân Sư phạm, đạt nhiều thành tích trong phong trào thi đua giáo viên giỏi cấp trường. Giỏi thiết kế bài giảng trực quan, tích hợp công nghệ vào giảng dạy, giúp học sinh hứng thú và ghi nhớ bài học tốt hơn.', 
	2,3, 2, NULL, NULL,0),
( N'Đặng Xuân Đích', N'Nam', '1985-06-12', '0926381293', 'xuandich@gmail.com', 'xuandich', 'team-4.jpg',
    N'Thạc sĩ Giáo dục học', N'Giáo viên có hơn 7 năm kinh nghiệm giảng dạy cấp 3, từng đảm nhận vai trò cố vấn học tập. Tâm huyết với nghề, luôn xây dựng môi trường học tập tích cực, hỗ trợ học sinh phát triển cả kiến thức lẫn kỹ năng sống.', 
	null, NULL, 2, NULL, NULL,0),
( N'Nguyễn Phi Hùng', N'Nam', '1983-12-22', '0956723961', 'phihung@gmail.com', 'phihung', 'team-5.jpg',
    N'Cử nhân Đào Đạo', N'Tốt nghiệp chương trình đào tạo giáo viên chất lượng cao, có kinh nghiệm dạy học sinh từ trung bình đến khá giỏi. Biết cách cá nhân hóa lộ trình học cho từng đối tượng, luôn đồng hành cùng học sinh trong quá trình tiến bộ.', 
	2, 4, 2, NULL, NULL,1),
( N'Vũ Minh Thư', N'Nữ', '1995-02-22', '0963812943', 'minhthu@gmail.com', 'minhthu', 'team-6.jpg',
    N'Thạc sĩ Giáo dục học', N'Tham gia nhiều khóa bồi dưỡng chuyên môn và phát triển năng lực giảng dạy. Kinh nghiệm tổ chức hoạt động học nhóm, thuyết trình và các hình thức đánh giá mới giúp học sinh chủ động tiếp thu kiến thức.', 
	3, 5, 2, NULL, NULL,0),
( N'Phạm Ngọc Hà', N'Nữ', '1980-06-18', '0963951903', 'ngocha@gmail.com', 'ngocha', 'team-7.jpg',
    N'Thạc sĩ Đại học Sư Phạm', N'Từng công tác tại nhiều trường THPT lớn với vai trò giáo viên chủ nhiệm. Có kỹ năng quản lý lớp, nắm bắt tâm lý học sinh tốt, phối hợp hiệu quả với phụ huynh trong việc định hướng học tập và rèn luyện cho học sinh.', 
	3, 6, 2, NULL, NULL,0),
( N'Nguyễn Duy Khánh', N'Nam', '1980-12-03', '0939126385', 'duykhanh@gmail.com', 'duykhanh', 'team-8.jpg',
    N'Thạc sĩ Đại học QUốc Tế', N'Thạc sĩ tốt nghiệp từ đại học quốc tế, có khả năng sử dụng tiếng Anh trong giảng dạy. Phong cách dạy học sáng tạo, linh hoạt, khuyến khích học sinh đặt câu hỏi và tư duy phản biện trong mỗi buổi học.', 
	4, 7, 2, NULL, NULL,1),
( N'Hà Hương Giang', N'Nữ', '1993-03-02', '09279041639', 'huonggiang@gmail.com', 'huonggiang', 'team-9.jpg',
    N'Thạc sĩ Giáo dục học', N'Giáo viên trẻ, năng động, tốt nghiệp loại xuất sắc, có phương pháp giảng dạy hiện đại, kết hợp giữa lý thuyết và thực hành. Từng tổ chức nhiều buổi học trải nghiệm và hoạt động ngoại khóa hiệu quả.', 
	4, 8, 2, NULL, NULL,0),
( N'Lý Trần A Khương', N'Nam', '1992-10-19', '0937951403', 'akhuong@gmail.com', 'akhuong', 'team-10.jpg',
    N'Thạc sĩ Giáo dục học', N'Giáo viên có kinh nghiệm giảng dạy chương trình chuẩn và nâng cao, từng tham gia biên soạn tài liệu học tập. Luôn tạo động lực cho học sinh học tập tích cực, được học sinh yêu quý và phụ huynh tin tưởng.', 
	null, NULL, 2, NULL, NULL,1),
( N'Ngô Thị Mai', N'Nữ', '1987-03-11', '0912345671', 'ngomai@gmail.com', 'ngomai', 'team-11.jpg',
  N'Cử nhân Giáo dục', N'Cô Mai là giáo viên có hơn 6 năm kinh nghiệm, nổi bật với khả năng kết nối với học sinh và tạo môi trường học tích cực.', 
  5, 9, 2, NULL, NULL, 0),

( N'Lê Thành Công', N'Nam', '1980-07-24', '0912345672', 'thanhcong@gmail.com', 'thanhcong', 'team-12.jpg',
  N'Thạc sĩ Sư phạm', N'Với phong cách giảng dạy hài hước và linh hoạt, thầy Công giúp học sinh tiếp thu bài nhanh chóng và yêu thích môn học.', 
  5, 10, 2, NULL, NULL, 0),

( N'Phạm Hồng Nhung', N'Nữ', '1985-09-13', '0912345673', 'hongnhung@gmail.com', 'hongnhung', 'team-13.jpg',
  N'Cử nhân Giáo dục', N'Cô Nhung có phương pháp dạy học hiện đại, khuyến khích học sinh tư duy phản biện và sáng tạo.', 
  1, 11, 2, NULL, NULL, 0),

( N'Trần Văn Hưng', N'Nam', '1982-04-09', '0912345674', 'vanhung@gmail.com', 'vanhung', 'team-14.jpg',
  N'Thạc sĩ Giáo dục học', N'Thầy Hưng là giáo viên giàu kinh nghiệm, luôn cập nhật phương pháp giảng dạy mới nhằm nâng cao hiệu quả học tập.', 
  NULL, NULL, 2, NULL, NULL, 0),

( N'Hoàng Thị Tuyết', N'Nữ', '1990-12-01', '0912345675', 'hoangtuyet@gmail.com', 'hoangtuyet', 'team-15.jpg',
  N'Cử nhân Giáo dục', N'Cô Tuyết là người truyền cảm hứng, thường xuyên tổ chức các buổi học sáng tạo và tương tác cao.', 
  1, 12, 2, NULL, NULL, 0),

( N'Nguyễn Quang Vinh', N'Nam', '1986-06-06', '0912345676', 'quangvinh@gmail.com', 'quangvinh', 'team-16.jpg',
  N'Thạc sĩ Sư phạm', N'Thầy Vinh giảng dạy chắc chắn, có khả năng xây dựng chương trình học phù hợp cho từng nhóm học sinh.', 
  2, 13, 2, NULL, NULL, 0),

( N'Đỗ Lệ Hằng', N'Nữ', '1984-10-10', '0912345677', 'lehang@gmail.com', 'lehang', 'team-17.jpg',
  N'Cử nhân Giáo dục', N'Cô Hằng từng đảm nhận nhiều lớp bồi dưỡng học sinh yếu, giúp học sinh lấy lại căn bản và tiến bộ rõ rệt.', 
  NULL, NULL, 2, NULL, NULL, 0),

( N'Vũ Minh Hải', N'Nam', '1989-01-25', '0912345678', 'minhhai@gmail.com', 'minhhai', 'team-18.jpg',
  N'Thạc sĩ Giáo dục học', N'Thầy Hải kết hợp phương pháp truyền thống với công nghệ để tạo bài học sinh động và hấp dẫn.', 
  2, 14, 2, NULL, NULL, 0),

( N'Lương Ngọc Hân', N'Nữ', '1991-05-17', '0912345679', 'ngochan@gmail.com', 'ngochan', 'team-19.jpg',
  N'Cử nhân Giáo dục', N'Cô Hân có khả năng truyền đạt rõ ràng, mạch lạc, giúp học sinh hiểu bài dễ dàng.', 
  3, 15, 2, NULL, NULL, 0),

( N'Huỳnh Văn Tâm', N'Nam', '1988-08-08', '0912345680', 'vantam@gmail.com', 'vantam', 'team-20.jpg',
  N'Thạc sĩ Giáo dục học', N'Thầy Tâm là người nghiêm khắc nhưng thân thiện, luôn quan tâm và hỗ trợ học sinh trong quá trình học tập.', 
  NULL, NULL, 2, NULL, NULL, 0),
(N'Nguyễn Văn An', N'Nam', '2008-08-05', '0963802518', 'haidvhe186726@fpt.edu.vn', 'vanan', 
	'hocvien-1.jpg',  N'90,15 điểm - Thi Đánh giá tư duy 2024. Học sinh giỏi cấp tỉnh Toán. Được tuyển thẳng vào Đại học Bách Khoa Hà Nội.', 
	null, 1,2, 3, 'parentan@gmail.com', '0853825851',null
),
(N'Kiều Thùy Trang', N'Nữ', '2007-12-31', '0954912539', 'thuytrang@gmail.com', 'thuytrang', 
	'hocvien-2.jpg', N'Đạt học bổng Đại học Y Hà Nội 2024. Đạt thủ khoa kỳ thi ĐGNL toàn quốc 2024. Đạt IELTS 7,5 khi đang học lớp 11. Tuyển thẳng vào Đại học Y Hà Nội.', 
	null, 3,15, 3, 'parenttrang@gmail.com', '0853914273',null
),
(N'Tôn Anh Dũng', N'Nam', '2009-10-21', '0943718352', 'anhdung@gmail.com', 'anhdung', 
	'hocvien-3.jpg', N'Giải Nhì HSG Quốc gia Vật lý. 9,5 điểm thi Tin học quốc tế MOS. Được học bổng toàn phần tại FPT University.', 
	null, 2,10, 3, 'parentdung@gmail.com', '0854729516',null
),
(N'Nguyễn Thùy Linh', N'Nữ', '2008-11-02', '0972913739', 'thuylinh@gmail.com', 'thuylinh', 
	'hocvien-4.jpg', N'Học bổng toàn phần của trường THPT Thượng Cát 2024. GPA 9,5 suốt 3 năm THPT. Đạt thủ khoa kỳ thi học sinh giỏi môn Văn 2024.', 
	null, 4,20, 3, 'parentlinh@gmail.com', '0864912530',null
),
(N'Hoàng Xuân Nam', N'Nam', '2007-05-17', '0948261849', 'xuannam@gmail.com', 'xuannam', 
	'hocvien-5.jpg', N'127 điểm - ĐGNL ĐHQG TPHCM 2024. Thành viên đội tuyển HSG quốc gia môn Toán 2023. Học bổng tài năng Viettel.',
	null, 5,30, 3, 'parentnam@gmail.com', '0863904106',null
),
(N'Nguyễn Minh Đức', N'Nam', '2009-09-14', '0958014825', 'minhduc@gmail.com', 'minhduc', 
	'hocvien-6.jpg', N'9,75 điểm thi tốt nghiệp Toán. Đạt IELTS 7,5 khi đang học lớp 11. Top 10 thí sinh xuất sắc toàn quốc. Học bổng OISP Đại học Quốc gia Hà Nội.', 
	null, 1,4, 3, 'parentduc@gmail.com', '0848240613',null
),
(N'Kiều Mỹ Anh', N'Nữ', '2007-07-21', '0968291304', 'myanh@gmail.com', 'myanh', 
	'hocvien-7.jpg', N'28,5/30 kỳ thi THPT Quốc gia. Giải Nhì Quốc gia môn Tiếng Anh. Học bổng toàn phần Đại học FPT. Tuyển thẳng vào Đại học Y Hà Nội.',
	null, 2,12, 3, 'parentanh@gmail.com', '0864913924',null
),
(N'Dư Đức Quang', N'Nam', '2008-12-11', '0969241793', 'ducquang@gmail.com', 'ducquang', 
	'hocvien-8.jpg', N'9,8 điểm Vật lý kỳ thi tốt nghiệp THPT Quốc gia. Huy chương Bạc Olympic 30/4. Đỗ Đại học Y Hà Nội với 29,5 điểm.',
	null, 3,17, 3, 'parentquang@gmail.com', '0858148392',null
),
(N'Hoàng Tân Phúc', N'Nam', '2009-01-21', '0968319526', 'tanphuc@gmail.com', 'tanphuc', 
	'hocvien-9.jpg', N'131 điểm kỳ thi ĐGNL Hà Nội. Học sinh giỏi cấp tỉnh môn Hóa. Giải Nhì Quốc gia môn Tiếng Anh. Nhận học bổng VietHope',
	null, 4,19, 3, 'parentphuc@gmail.com', '0853914379',null
),
(N'Lương Thúy Quỳnh', N'Nữ', '2007-03-16', '0959317053', 'thuyquynh@gmail.com', 'thuyquynh', 
	'hocvien-10.jpg', N'GPA 9,8, đứng đầu lớp chuyên Văn. Giải Nhất cấp tỉnh môn Văn. Học bổng nữ sinh tiêu biểu 2024. Top 10 thí sinh xuất sắc toàn quốc.', 
	null, 5,27, 3, 'parentquynh@gmail.com', '0876319635',null
),
(N'Phạm Ngọc Linh', N'Nữ', '2007-07-19', '0987301583', 'ngoclinhhh@gmail.com', 'ngoclinhhh', 
	'hocvien-11.jpg', null, 
	null, 2,23, 3, null, null,null
),
(N'Hạ Lan', N'Nữ', '2009-11-09', '0958163062', 'halan@gmail.com', 'halan', 
	'hocvien-12.jpg', null, 
	null, 2,4, 3, null, null,null
),
(N'Đỗ Việt Long', N'Nam', '2008-02-12', '0937169538', 'vietlong@gmail.com', 'vietlong', 
	'hocvien-13.jpg', null, 
	null, 5,19, 3, null, null,null
),
(N'Lương Ngọc Giang', N'Nam', '2008-08-27', '0969251706', 'ngocgiang@gmail.com', 'ngocgiang', 
	'hocvien-14.jpg', null, 
	null, 3,15, 3, null, null,null
),
(N'Nguyễn Hữu Phước', N'Nam', '2009-03-21', '0969606216', 'huuphuoc@gmail.com', 'huuphuoc', 
	'hocvien-15.jpg', null, 
	null, 4,7, 3, null, null,null
),
(N'Nguyễn Huy Hoàng', N'Nam', '2009-07-27', '0961520520', 'huyhoang@gmail.com', 'huyhoang', 
	'hocvien-16.jpg', null, 
	null, 2,3, 3, null, null,null
),
(N'Bùi Trường Sơn', N'Nam', '2007-03-15', '0957926394', 'truongson@gmail.com', 'truongson', 
	'hocvien-17.jpg', null, 
	null, 5,30, 3, null, null,null
),
(N'Bùi Mai Uyên', N'Nữ', '2007-10-19', '0960926438', 'maiuyen@gmail.com', 'maiuyen', 
	'hocvien-18.jpg', null, 
	null, 2,24, 3, null, null,null
),
(N'Nguyễn Khánh Vân', N'Nữ', '2007-11-29', '0961808253', 'khanhvan@gmail.com', 'khanhvan', 
	'hocvien-19.jpg', null, 
	null, 3,25, 3, null, null,null
),
(N'Nguyễn Xuân Trường', N'Nam', '2007-04-17', '0959851739', 'xuantruong@gmail.com', 'xuantruong', 
	'hocvien-20.jpg', null, 
	null, 1,21, 3, null, null,null
),
(N'Văn Thị Như', N'Nữ', '2004-11-23', '0974279361', 'nhuvthe181329@fpt.edu.vn', 'vannhu', 'manager.jpg',
    null, null, null, NULL, 4, 
    NULL, NULL,null
)
;
select * from [user]
select * from school



INSERT INTO [User] (
    FullName, Gender, BirthDate, phone, email, pass, avatar,
    Certi, Descrip, SchoolID, roleID, ParentEmail, ParentPhone,isHot,SchoolClassID
)
values


-- 6. Môn học (Subject)(Toán,Văn,Anh,Sử,Địa,Lí,Hóa,Sinh)
insert into Subjects(SubjectName,ImageSubject)
values
(N'Toán','toan.jpg'),
(N'Ngữ Văn','van.jpg'),
(N'Tiếng Anh','anh.jpg'),
(N'Lịch Sử','su.jpg'),
(N'Địa Lý','dia.jpg'),
(N'Sinh Học','sinh.jpg'),
(N'Hóa Học','hoa.jpg'),
(N'Vật Lý','ly.jpg');
select *from [user]
insert into TeacherSubjects(UserID,SubjectID)
values
(2,1),
(2,8);
--7.Ca hoc
insert into Shiftlearn(Start_time,End_time)
values
('07:30:00', '09:30:00'),
('09:40:00', '11:40:00'),
('13:00:00', '15:00:00'),
('15:10:00', '17:10:00'),
('17:20:00', '19:20:00'),
('19:30:00', '21:30:00');

--8.Room
insert into Room(roomName)
values
('E1'),
('E2'),
('E3'),
('E4'),
('E5'),
('E6'),
('E7'),
('E8'),
('E9'),
('E10'),
('E11'),
('E12'),
('E13'),
('E14'),
('E15');

-- 9. Khóa học(TutoringClass)
insert into TutoringClass(ClassName,ImageTutoring,Descrip,
isHot,SubjectID,StartDate,EndDate,
Tuitionfee,gradeID)
values
(N'Ôn thi đánh giá tư duy - TSA','course-1.jpg',N'Củng cố kiến thức cho học sinh ôn thi THPT Quốc gia',
1,1,'2025-06-15','2025-09-15','100000',3),
(N'Ôn thi đánh giá năng lực Toán','course-11.jpg',N'Củng cố kiến thức cho học sinh ôn thi THPT Quốc gia',
1,1,'2025-06-10','2025-09-10','120000',3),
(N'Ôn thi đánh giá năng lực Văn','course-12.jpg',N'Củng cố kiến thức cho học sinh ôn thi THPT Quốc gia',
1,2,'2025-06-10','2025-09-10','120000',3),
(N'Ôn thi đại học cấp tốc môn Vật Lý','course-13.jpg',N'Củng cố kiến thức cho học sinh ôn thi THPT Quốc gia',
1,8,'2025-06-10','2025-09-10','120000',3),
(N'Ôn thi đại học cấp tốc môn Lịch Sử','course-14.jpg',N'Củng cố kiến thức cho học sinh ôn thi THPT Quốc gia',
1,4,'2025-06-10','2025-09-10','120000',3),
(N'Ôn thi đại học cấp tốc môn Hóa Học','course-15.jpg',N'Củng cố kiến thức cho học sinh ôn thi THPT Quốc gia',
1,7,'2025-06-10','2025-09-10','120000',3),
(N'Tổng ôn Toán 10','cat-17.jpg',N'Củng cố kiến thức bài giảng trên lớp cho học sinh khối 10',
0,1,'2025-08-01','2026-08-01','80000',1),
(N'Tổng ôn Văn 11','cat-14.jpg',N'Củng cố kiến thức bài giảng trên lớp cho học sinh khối 11',
0,2,'2025-08-01','2026-08-01','80000',2),
(N'Tổng ôn Tiếng Anh 12','cat-5.jpg',N'Củng cố kiến thức bài giảng trên lớp cho học sinh khối 12',
0,3,'2025-08-01','2026-08-01','80000',3)
;

--10.Class Group
insert into ClassGroup(TutoringClassID,ClassGroupName,TeacherID)
values
(1,N'Ôn thi đánh giá tư duy - TSA - 01',3),
(1,N'Ôn thi đánh giá tư duy - TSA - 02',3),
(1,N'Ôn thi đánh giá tư duy - TSA - 03',2),
(1,N'Ôn thi đánh giá tư duy - TSA - 04',4),
(1,N'Ôn thi đánh giá tư duy - TSA - 05',2),
(1,N'Ôn thi đánh giá tư duy - TSA - 06',4),
(2,N'Ôn thi đánh giá năng lực Toán - 01',3),
(2,N'Ôn thi đánh giá năng lực Toán - 02',5),
(2,N'Ôn thi đánh giá năng lực Toán - 03',5),
(2,N'Ôn thi đánh giá năng lực Toán - 04',2),
(2,N'Ôn thi đánh giá năng lực Toán - 05',3),
(2,N'Ôn thi đánh giá năng lực Toán - 06',3),
(3,N'Ôn thi đánh giá năng lực Văn - 01',6),
(3,N'Ôn thi đánh giá năng lực Văn - 02',7),
(3,N'Ôn thi đánh giá năng lực Văn - 03',6),
(3,N'Ôn thi đánh giá năng lực Văn - 04',7),
(3,N'Ôn thi đánh giá năng lực Văn - 05',6),
(3,N'Ôn thi đánh giá năng lực Văn - 06',6),
(4,N'Ôn thi đại học cấp tốc môn Vật Lý - 01',2),
(4,N'Ôn thi đại học cấp tốc môn Vật Lý - 02',3),
(4,N'Ôn thi đại học cấp tốc môn Vật Lý - 03',4),
(4,N'Ôn thi đại học cấp tốc môn Vật Lý - 04',5),
(4,N'Ôn thi đại học cấp tốc môn Vật Lý - 05',2),
(4,N'Ôn thi đại học cấp tốc môn Vật Lý - 06',4),
(5,N'Ôn thi đại học cấp tốc môn Lịch Sử - 01',6),
(5,N'Ôn thi đại học cấp tốc môn Lịch Sử - 02',8),
(5,N'Ôn thi đại học cấp tốc môn Lịch Sử - 03',6),
(5,N'Ôn thi đại học cấp tốc môn Lịch Sử - 04',8),
(5,N'Ôn thi đại học cấp tốc môn Lịch Sử - 05',8),
(5,N'Ôn thi đại học cấp tốc môn Lịch Sử - 06',8),
(6,N'Ôn thi đại học cấp tốc môn Hóa Học - 01',9),
(6,N'Ôn thi đại học cấp tốc môn Hóa Học - 02',10),
(6,N'Ôn thi đại học cấp tốc môn Hóa Học - 03',9),
(6,N'Ôn thi đại học cấp tốc môn Hóa Học - 04',9),
(6,N'Ôn thi đại học cấp tốc môn Hóa Học - 05',10),
(6,N'Ôn thi đại học cấp tốc môn Hóa Học - 06',9),
(7,N'Tổng ôn Toán 10 - 01',2),
(7,N'Tổng ôn Toán 10 - 02',3),
(7,N'Tổng ôn Toán 10 - 03',4),
(7,N'Tổng ôn Toán 10 - 04',5),
(8,N'Tổng ôn Văn 11 - 01',6),
(8,N'Tổng ôn Văn 11 - 02',7),
(8,N'Tổng ôn Văn 11 - 03',6),
(8,N'Tổng ôn Văn 11 - 04',7),
(9,N'Tổng ôn Tiếng Anh 12 - 01',11),
(9,N'Tổng ôn Tiếng Anh 12 - 02',11);


-- 11. Điểm danh của lớp (Attendance)
insert into Attendance(ClassGroupID,UserID,IsPresent)
values
(1,23,1),
(1,26,0);

-- 11. Bình luận (Comment)
INSERT INTO Comment (UserID, CommentText, Rating)
VALUES 
(22, N'Em cảm ơn Edura và các thầy cô rất nhiều ạ.',5),
(23, N'Không khí lớp học thoải mái, thầy cô nhiệt tình, bạn bè thân thiện.', 4),
(24, N'Em rất biết ơn vì những kiến thức bổ ích từ Edura.', 5),
(25, N'Chương trình học dễ hiểu, thầy cô tận tâm và nhiệt huyết.', 5),
(26, N'Nhờ Edura, em đã cải thiện rõ rệt cả kiến thức lẫn kỹ năng làm bài.',5),
(27, N'Em chưa từng nghĩ mình sẽ thi tốt thế nếu không có Edura đồng hành.',4),
(28, N'Lộ trình học rõ ràng, dễ theo sát và hiệu quả. Em cảm ơn trung tâm!',5),
(29, N'Hệ thống bài tập và video rất chất lượng, giúp em tự tin thi cử.', 5),
(30, N'Chương trình học rất sát đề, dễ ôn, dễ nhớ và có hệ thống.', 5),
(31, N'Khóa học không chỉ giúp em học tốt mà còn tự tin thể hiện bản thân hơn.', 4);


-- 12. Tài liệu (Document)

select*from Document
insert into Classify(TypeDoc)
values
(N'Đề cương ôn thi THPTQG'),
(N'Đề cương kiểm tra giữa kỳ'),
(N'Đề cương kiểm tra cuối kỳ'),
(N'Đề cương kiểm tra 1 tiết'),
(N'Đề thi thử THPTQG'),
(N'Đề thi thử giữa kỳ'),
(N'Đề thi thử cuối kỳ'),
(N'Đề thi thử 1 tiết'),
(N'Ôn tập hàng ngày');


-- 13. Thông báo (Notification)
insert into Notifications(Title,Content,TargetRole,CreatedBy)
values
(N'Thông báo nghỉ lễ',N'Kính gửi các thầy cô giáo của trung tâm. Sắp tới là ngày Lễ Quốc Khánh ngày 2/9. Trung tâm của chúng ta sẽ nghỉ lễ 4 ngày từ ngày 1/9 tới ngày 4/9.
Từ ngày 5/9 trung tâm sẽ quay trở lại hoạt động bình thường. Mong các thầy cô nắm bắt thông tin và chúc các thầy cô có kì nghỉ lễ vui vẻ bên gia đình.',2,4);

-- 14. Thanh toán (Payment)
--insert into Payment(UserID,TutoringClassID,Amount)
--values
--(13,1,'100000');

--15. setting
insert into CenterInfo(CenterID,NameCenter, DescripCenter, DescripCenter1, AddressCenter, Logo, imageCenter, Phone, Email,YearOfWork)
values('1', N'Nâng Tầm Tri Thức - Kiến Tạo Tương Lai', N'Edura cam kết mang đến chất lượng giáo dục vượt trội với đội ngũ giáo viên tận tâm, phương pháp giảng dạy hiện đại và chương trình học bám sát thực tiễn.
Edura không chỉ chú ý đến việc truyền đạt kiến thức, mà còn khơi dậy niềm đam mê học tập, phát triển tư duy và định hướng thành công cho mỗi học sinh.
Chất lượng hôm nay là giá trị vững chắc cho ngày mai – đó là kim chỉ nam trong mọi hoạt động của Edura.', N'Edura – Kết nối tri thức, chắp cánh tương lai. Edura – Hỗ trợ giáo viên, nâng tầm học sinh. Edura – Nơi tri thức hội tụ, ước mơ thăng hoa.', N'Quận Hai Bà Trưng, Hà Nội', 'logo.jpg', 'about-1.jpg', '0912345678', 'edura@gmail.com',2007);


--16.Đăng ký dành cho người chưa có tài khoản
insert into TutoringRegistrationPending(FullName,Phone,Email,Gender,BirthDate,School,AddressSchool,Class,ParentPhone,ParentEmail,UserIntro,Confirmed)
values
(N'Dư Phương Linh','0987362174','phuonglinh@gmail.com',N'Nữ','03-04-2008',N'Trường THPT Nguyễn Huệ',N'Khu đô thị Cổ Nhuế - Bắc Từ Liêm - Hà Nội',N'Lớp 11B',null,null,null,1);

INSERT INTO banner(bannerImg, CenterID)
VALUES 
(N'teacher.jpg', 1),
(N'banner.jpg', 1),
(N'banner1.jpg', 1),
(N'banner2.jpg', 1);

--chạy trigger trước rồi quay ra chạy insert lịch mẫu này 
insert into ScheduleTemplate(DayOfWeek,shiftID,RoomID,TeacherID,ClassGroupID)
values
(7,1,1,3,1),
(7,2,2,3,2),
(1,3,1,2,3),
(2,4,2,4,4),
(1,5,1,2,5),
(2,6,2,4,6),
(3,1,2,3,7),
(3,2,1,5,8),
(3,3,2,5,9),
(2,4,1,2,10),
(5,5,2,3,11),
(5,6,1,3,12),
(7,1,3,6,13),
(1,2,4,7,14),
(7,3,3,6,15),
(2,4,4,7,16),
(4,5,3,6,17),
(4,6,4,6,18),
(2,1,4,2,19),
(3,2,3,3,20),
(2,3,4,4,21),
(6,4,3,5,22),
(2,5,4,2,23),
(4,6,3,4,24),
(1,1,5,6,25),
(6,2,6,8,26),
(5,3,5,6,27),
(6,4,6,8,28),
(6,5,5,8,29),
(6,6,6,8,30),
(1,1,6,9,31),
(5,2,5,10,32),
(1,3,6,9,33),
(1,4,5,9,34),
(5,5,6,10,35),
(1,6,5,9,36),
(1,1,7,2,37),
(3,3,8,3,38),
(4,4,7,4,39),
(6,6,8,5,40),
(5,2,7,6,41),
(1,3,9,7,42),
(5,5,10,6,43),
(2,6,9,7,44),
(2,4,9,11,45),
(2,5,10,11,46);

SELECT cg.ClassGroupName
FROM Schedule s
JOIN ClassGroup cg ON s.ClassGroupID = cg.ClassGroupID
WHERE s.UserID = 2
  AND s.DateLearn = CONVERT(date, GETDATE())
ORDER BY s.ShiftID ASC

insert into ClassGroup_Student(ClassGroupID,StudentID)
values
(1,23),
(1,26),
(7,28),
(7,31),
(16,32),
(22,38),
(27,39),
(34,40),
(46,41),
(41,22),
(42,25),
(43,29),
(44,34),
(41,35),
(38,24),
(39,27),
(40,30),
(38,33),
(39,36),
(40,37);
