select * from [user]
select u.UserID, FullName,avatar,Gender,BirthDate,phone,email,s.SchoolName,sc.ClassName, onlineStatus
from [User] u join School s on u.SchoolID = s.SchoolID
join TeacherClass tc on u.UserID = tc.UserID
join SchoolClass sc on tc.SchoolClassID = sc.SchoolClassID
where roleID = 3
update [user]
set onlineStatus = 0 where UserID = 56

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
alter table RequestActive
add DateRequest DATETIME DEFAULT GETDATE()
alter table RequestActive
add stt NVARCHAR(20) DEFAULT 'Pending'
select * from RequestActive
delete from RequestActive

select * from [user]
select * from RequestActive
select * from TeacherClass where userid = 23
select * from schoolclass
update TeacherClass set SchoolClassID = 3 where userID = 33
SELECT sc.SchoolClassID, sc.ClassName, sc.SchoolID FROM TeacherClass tc 
                 JOIN SchoolClass sc ON tc.SchoolClassID = sc.SchoolClassID 
                 WHERE tc.UserID = 33

				 update [user] 
                set email = 'thuytrang@gmail.com', phone = '0912121212', avatar = '/WebApplication3/image-loader/hocvien-2.jpg',
                descrip = 'huhu',
                SchoolID = 3
                where userid = 23

				SELECT schoolClassID, className, schoolID FROM SchoolClass WHERE schoolID = 1

select * from schoolclass
INSERT INTO [User] (
    FullName, Gender, BirthDate, phone, email, pass, avatar,
    Certi, Descrip, SchoolID, roleID, ParentEmail, ParentPhone,isHot,SchoolClassID
)
values
(N'Nguyễn Thị Mai', N'Nữ', '2008-03-12', '0912345670', 'nguyenthimai1@gmail.com', 
'nguyenthimai1', '/WebApplication3/image-loader/avatamacdinh.jpg', 
null, null, 1, 3, null, null,0, 2),
(N'Tạ Văn Bình', N'Nam', '2007-08-14', '0922113344', 'tavanbinh1@gmail.com', 'tavanbinh1', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 5, 3, null, null, 0, 29),
(N'Phạm Hữu Nam', N'Nam', '2008-02-23', '0927589434', 'phamhuunam2@gmail.com', 'phamhuunam2', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 4, 3, null, null, 0, 18),
(N'Nguyễn Văn Sơn', N'Nam', '2007-05-15', '0828230347', 'nguyenvanson3@gmail.com', 'nguyenvanson3', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 4, 3, null, null, 0, 28),
(N'Lê Thị Lan', N'Nữ', '2008-07-20', '0932001895', 'lethilan4@gmail.com', 'lethilan4', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 5, 3, null, null, 0, 20),
(N'Hồ Ngọc Linh', N'Nữ', '2009-02-19', '0891273829', 'hongoclinh5@gmail.com', 'hongoclinh5', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 1, 3, null, null, 0, 22),
(N'Phan Hữu Khôi', N'Nam', '2009-07-10', '0921302761', 'phanhuukhoi6@gmail.com', 'phanhuukhoi6', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 3, 3, null, null, 0, 26),
(N'Phạm Ngọc Lan', N'Nữ', '2009-08-06', '0921422451', 'phamngoclan7@gmail.com', 'phamngoclan7', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 14, 3, null, null, 0, 36),
(N'Phan Văn Hiếu', N'Nam', '2007-02-25', '0915561184', 'phanvanhieu8@gmail.com', 'phanvanhieu8', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 4, 3, null, null, 0, 18),
(N'Phan Thanh Sơn', N'Nam', '2009-02-12', '0870345437', 'phanthanhson9@gmail.com', 'phanthanhson9', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 1, 3, null, null, 0, 35),
(N'Lê Ngọc Châu', N'Nữ', '2008-08-08', '0917342712', 'lengocchau10@gmail.com', 'lengocchau10', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 15, 3, null, null, 0, 38),
(N'Hồ Hữu Khôi', N'Nam', '2008-06-28', '0983604794', 'hohuukhoi11@gmail.com', 'hohuukhoi11', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 2, 3, null, null, 0, 24),
(N'Phạm Thanh Hưng', N'Nam', '2009-01-14', '0891423161', 'phamthanhhung12@gmail.com', 'phamthanhhung12', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 5, 3, null, null, 0, 29),
(N'Vũ Ngọc Vân', N'Nữ', '2007-02-28', '0895745585', 'vungocvan13@gmail.com', 'vungocvan13', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 2, 3, null, null, 0, 23),
(N'Ngô Văn Dũng', N'Nam', '2007-09-21', '0890507594', 'ngovandung14@gmail.com', 'ngovandung14', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 3, 3, null, null, 0, 25),
(N'Hoàng Ngọc Hương', N'Nữ', '2009-04-22', '0880110491', 'hoangngochuong15@gmail.com', 'hoangngochuong15', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 1, 3, null, null, 0, 21),
(N'Nguyễn Văn Bảo', N'Nam', '2008-11-02', '0986231886', 'nguyenvanbao31@gmail.com', 'nguyenvanbao31', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 4, 3, null, null, 0, 17),
(N'Nguyễn Minh Quân', N'Nam', '2008-01-11', '0928321473', 'nguyenminhquan32@gmail.com', 'nguyenminhquan32', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 3, 3, null, null, 0, 15),
(N'Lê Thị Hòa', N'Nữ', '2008-05-24', '0912034232', 'lethihoa33@gmail.com', 'lethihoa33', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 2, 3, null, null, 0, 13),
(N'Hoàng Minh Huy', N'Nam', '2009-08-17', '0891472753', 'hoangminhhuy34@gmail.com', 'hoangminhhuy34', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 1, 3, null, null, 0, 22),
(N'Trần Thanh Tâm', N'Nữ', '2007-07-19', '0871213984', 'tranthanhtam35@gmail.com', 'tranthanhtam35', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 1, 3, null, null, 0, 11),
(N'Lê Hồng Phúc', N'Nam', '2008-12-06', '0912391284', 'lehongphuc36@gmail.com', 'lehongphuc36', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 2, 3, null, null, 0, 14),
(N'Phạm Bích Ngọc', N'Nữ', '2007-09-03', '0931235712', 'phambichngoc37@gmail.com', 'phambichngoc37', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 5, 3, null, null, 0, 20),
(N'Đỗ Thị Duyên', N'Nữ', '2009-04-07', '0972381243', 'dothiduyen38@gmail.com', 'dothiduyen38', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 4, 3, null, null, 0, 17),
(N'Ngô Văn Nam', N'Nam', '2007-03-10', '0973642184', 'ngovannam39@gmail.com', 'ngovannam39', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 3, 3, null, null, 0, 16),
(N'Phan Minh Khoa', N'Nam', '2009-10-09', '0961123453', 'phanminhkhoa40@gmail.com', 'phanminhkhoa40', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 5, 3, null, null, 0, 19),
(N'Nguyễn Khánh Huyền', N'Nữ', '2007-05-18', '0919921475', 'nguyenkhanhhuyen41@gmail.com', 'nguyenkhanhhuyen41', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 2, 3, null, null, 0, 14),
(N'Lê Văn Minh', N'Nam', '2009-03-03', '0928421352', 'levanminh42@gmail.com', 'levanminh42', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 14, 3, null, null, 0, 36),
(N'Trần Thị Quỳnh', N'Nữ', '2007-12-31', '0937234571', 'tranthiquynh43@gmail.com', 'tranthiquynh43', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 1, 3, null, null, 0, 21),
(N'Hoàng Văn Phúc', N'Nam', '2008-08-23', '0919471325', 'hoangvanphuc44@gmail.com', 'hoangvanphuc44', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 14, 3, null, null, 0, 37),
(N'Nguyễn Ngọc Mai', N'Nữ', '2009-06-09', '0982384351', 'nguyenngocmai45@gmail.com', 'nguyenngocmai45', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 3, 3, null, null, 0, 25),
(N'Phạm Hồng Sơn', N'Nam', '2008-10-14', '0963492845', 'phamhongson46@gmail.com', 'phamhongson46', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 5, 3, null, null, 0, 29),
(N'Lê Quỳnh Trang', N'Nữ', '2008-03-12', '0932917546', 'lequynhtrang47@gmail.com', 'lequynhtrang47', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 3, 3, null, null, 0, 26),
(N'Võ Đức Huy', N'Nam', '2009-11-25', '0923312675', 'voduchuy48@gmail.com', 'voduchuy48', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 4, 3, null, null, 0, 8),
(N'Nguyễn Thị Vân', N'Nữ', '2008-09-30', '0891357248', 'nguyenthivan49@gmail.com', 'nguyenthivan49', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 2, 3, null, null, 0, 13),
(N'Phan Văn Dũng', N'Nam', '2007-01-16', '0982716354', 'phanvandung50@gmail.com', 'phanvandung50', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 4, 3, null, null, 0, 7),
(N'Trần Hữu Nghĩa', N'Nam', '2007-04-26', '0913842653', 'tranhuunghia51@gmail.com', 'tranhuunghia51', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 2, 3, null, null, 0, 14),
(N'Lê Thị Như', N'Nữ', '2009-07-27', '0891532671', 'lethinhu52@gmail.com', 'lethinhu52', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 1, 3, null, null, 0, 11),
(N'Hoàng Minh Tuấn', N'Nam', '2008-11-06', '0975432712', 'hoangminhtuan53@gmail.com', 'hoangminhtuan53', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 3, 3, null, null, 0, 15),
(N'Nguyễn Hữu Đức', N'Nam', '2007-06-08', '0921482375', 'nguyenhuuduc54@gmail.com', 'nguyenhuuduc54', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 14, 3, null, null, 0, 37),
(N'Trần Thị Mỹ', N'Nữ', '2008-01-20', '0938129475', 'tranthimy55@gmail.com', 'tranthimy55', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 5, 3, null, null, 0, 20),
(N'Phạm Văn Hưng', N'Nam', '2009-02-04', '0983321471', 'phamvanhung56@gmail.com', 'phamvanhung56', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 2, 3, null, null, 0, 14),
(N'Lê Hoàng Nam', N'Nam', '2008-04-30', '0921478234', 'lehoangnam57@gmail.com', 'lehoangnam57', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 3, 3, null, null, 0, 16),
(N'Nguyễn Thị Ngọc', N'Nữ', '2007-08-12', '0971142375', 'nguyenthingoc58@gmail.com', 'nguyenthingoc58', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 5, 3, null, null, 0, 29),
(N'Trần Văn Lâm', N'Nam', '2009-09-01', '0898214321', 'tranvanlam59@gmail.com', 'tranvanlam59', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 2, 3, null, null, 0, 13),
(N'Phạm Ngọc Ánh', N'Nữ', '2008-06-15', '0961472351', 'phamngocanh60@gmail.com', 'phamngocanh60', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 4, 3, null, null, 0, 17),
(N'Vũ Thị Thanh', N'Nữ', '2007-10-18', '0913323121', 'vuthithanh31@gmail.com', 'vuthithanh31', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 3, 3, null, null, 0, 25),
(N'Nguyễn Thị Phương', N'Nữ', '2009-04-27', '0892657102', 'nguyenthiphuong32@gmail.com', 'nguyenthiphuong32', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 2, 3, null, null, 0, 23),
(N'Đặng Văn Minh', N'Nam', '2009-06-06', '0899438606', 'dangvanminh33@gmail.com', 'dangvanminh33', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 4, 3, null, null, 0, 18),
(N'Lê Hữu Dũng', N'Nam', '2008-05-15', '0913254021', 'lehuudung34@gmail.com', 'lehuudung34', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 14, 3, null, null, 0, 37),
(N'Hoàng Thị Huyền', N'Nữ', '2008-12-30', '0902738264', 'hoangthihuyen35@gmail.com', 'hoangthihuyen35', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 1, 3, null, null, 0, 12),
(N'Trần Văn Hưng', N'Nam', '2008-11-03', '0887683411', 'tranvanhung36@gmail.com', 'tranvanhung36', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 1, 3, null, null, 0, 11),
(N'Nguyễn Hữu Long', N'Nam', '2009-02-14', '0871234125', 'nguyenhuulong37@gmail.com', 'nguyenhuulong37', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 4, 3, null, null, 0, 17),
(N'Lê Thị Thảo', N'Nữ', '2007-08-09', '0970919821', 'lethithao38@gmail.com', 'lethithao38', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 5, 3, null, null, 0, 19),
(N'Đặng Văn Trường', N'Nam', '2008-04-10', '0932152434', 'dangvantruong39@gmail.com', 'dangvantruong39', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 3, 3, null, null, 0, 16),
(N'Ngô Thị Mai', N'Nữ', '2009-11-01', '0928871763', 'ngothimai40@gmail.com', 'ngothimai40', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 2, 3, null, null, 0, 14),
(N'Phan Hữu Lợi', N'Nam', '2007-10-14', '0927560025', 'phanhuuloi41@gmail.com', 'phanhuuloi41', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 4, 3, null, null, 0, 18),
(N'Nguyễn Văn Hào', N'Nam', '2009-08-12', '0892198622', 'nguyenvanhao42@gmail.com', 'nguyenvanhao42', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 1, 3, null, null, 0, 2),
(N'Hồ Thị Hạnh', N'Nữ', '2008-07-29', '0876541902', 'hothihanh43@gmail.com', 'hothihanh43', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 1, 3, null, null, 0, 1),
(N'Trịnh Văn Tài', N'Nam', '2008-01-08', '0911430083', 'trinhvantai44@gmail.com', 'trinhvantai44', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 1, 3, null, null, 0, 1),
(N'Lê Văn Khánh', N'Nam', '2009-01-10', '0883838138', 'levankhanh45@gmail.com', 'levankhanh45', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 1, 3, null, null, 0, 2),
(N'Nguyễn Thị Như', N'Nữ', '2007-09-14', '0921688760', 'nguyenthinhu46@gmail.com', 'nguyenthinhu46', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 4, 3, null, null, 0, 27),
(N'Trần Văn Long', N'Nam', '2009-05-21', '0927456632', 'tranvanlong47@gmail.com', 'tranvanlong47', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 5, 3, null, null, 0, 19),
(N'Lưu Thị Mai', N'Nữ', '2008-12-01', '0938521634', 'luuthimai48@gmail.com', 'luuthimai48', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 1, 3, null, null, 0, 1),
(N'Nguyễn Văn Khôi', N'Nam', '2008-03-05', '0893341284', 'nguyenvankhoi49@gmail.com', 'nguyenvankhoi49', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 1, 3, null, null, 0, 2),
(N'Hoàng Hữu Hậu', N'Nam', '2007-11-15', '0872148821', 'hoanghuuhau50@gmail.com', 'hoanghuuhau50', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 1, 3, null, null, 0, 12),
(N'Phan Thị Mỹ', N'Nữ', '2009-10-09', '0902146634', 'phanthimy51@gmail.com', 'phanthimy51', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 2, 3, null, null, 0, 3),
(N'Trần Hữu Ngọc', N'Nam', '2008-02-24', '0971928564', 'tranhuungoc52@gmail.com', 'tranhuungoc52', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 1, 3, null, null, 0, 11),
(N'Huỳnh Thị Mai', N'Nữ', '2007-06-18', '0914457834', 'huynhthimai53@gmail.com', 'huynhthimai53', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 3, 3, null, null, 0, 15),
(N'Lê Văn Hiếu', N'Nam', '2009-09-11', '0882753463', 'levanhieu54@gmail.com', 'levanhieu54', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 1, 3, null, null, 0, 12),
(N'Nguyễn Thị Thanh', N'Nữ', '2008-06-16', '0901623412', 'nguyenthithanh55@gmail.com', 'nguyenthithanh55', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 2, 3, null, null, 0, 13),
(N'Trần Ngọc Quỳnh', N'Nữ', '2007-05-30', '0907463784', 'tranngocquynh56@gmail.com', 'tranngocquynh56', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 4, 3, null, null, 0, 28),
(N'Đỗ Văn Cường', N'Nam', '2008-11-23', '0973218452', 'dovancuong57@gmail.com', 'dovancuong57', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 2, 3, null, null, 0, 14),
(N'Lê Thị Hà', N'Nữ', '2009-08-15', '0912552164', 'lethiha58@gmail.com', 'lethiha58', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 1, 3, null, null, 0, 2),
(N'Phan Văn Bình', N'Nam', '2009-06-03', '0885632148', 'phanvanbinh59@gmail.com', 'phanvanbinh59', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 1, 3, null, null, 0, 1),
(N'Trần Thị Thu', N'Nữ', '2008-12-17', '0927653410', 'tranthithu60@gmail.com', 'tranthithu60', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 1, 3, null, null, 0, 11),
(N'Nguyễn Hữu Đức', N'Nam', '2008-03-03', '0973123478', 'nguyenhuuduc31@gmail.com', 'nguyenhuuduc31', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 4, 3, null, null, 0, 17),
(N'Lê Ngọc Trâm', N'Nữ', '2009-01-14', '0829876511', 'lengoctram32@gmail.com', 'lengoctram32', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 2, 3, null, null, 0, 14),
(N'Phạm Văn Huy', N'Nam', '2007-04-05', '0923673488', 'phamvanhuy33@gmail.com', 'phamvanhuy33', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 2, 3, null, null, 0, 23),
(N'Trần Hữu Toàn', N'Nam', '2008-08-19', '0987212344', 'tranhuutoan34@gmail.com', 'tranhuutoan34', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 14, 3, null, null, 0, 36),
(N'Vũ Thị Mai', N'Nữ', '2009-09-30', '0911234972', 'vuthimai35@gmail.com', 'vuthimai35', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 1, 3, null, null, 0, 22),
(N'Ngô Văn Khải', N'Nam', '2007-12-12', '0938721435', 'ngovankhai36@gmail.com', 'ngovankhai36', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 15, 3, null, null, 0, 38),
(N'Hoàng Hữu Phát', N'Nam', '2008-05-25', '0877123491', 'hoanghuuphat37@gmail.com', 'hoanghuuphat37', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 1, 3, null, null, 0, 21),
(N'Trịnh Ngọc Dung', N'Nữ', '2009-06-18', '0981734210', 'trinhngocdung38@gmail.com', 'trinhngocdung38', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 4, 3, null, null, 0, 27),
(N'Bùi Thị Thu', N'Nữ', '2007-10-29', '0923419983', 'buithithu39@gmail.com', 'buithithu39', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 5, 3, null, null, 0, 20),
(N'Phan Văn Hòa', N'Nam', '2009-04-04', '0884447651', 'phanvanhoa40@gmail.com', 'phanvanhoa40', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 2, 3, null, null, 0, 4),
(N'Vũ Văn Ngọc', N'Nam', '2007-03-07', '0917663487', 'vuvanngoc41@gmail.com', 'vuvanngoc41', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 3, 3, null, null, 0, 6),
(N'Lê Thị Ngân', N'Nữ', '2008-01-22', '0974738282', 'lethingan42@gmail.com', 'lethingan42', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 1, 3, null, null, 0, 22),
(N'Đỗ Hữu Duy', N'Nam', '2009-02-03', '0897345832', 'dohuuduy43@gmail.com', 'dohuuduy43', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 5, 3, null, null, 0, 9),
(N'Trương Ngọc Hải', N'Nam', '2008-10-15', '0891329987', 'truongngocha44@gmail.com', 'truongngocha44', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 5, 3, null, null, 0, 10),
(N'Trịnh Thị Huyền', N'Nữ', '2008-09-01', '0890923182', 'trinhthihuyen45@gmail.com', 'trinhthihuyen45', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 2, 3, null, null, 0, 4),
(N'Nguyễn Văn Phúc', N'Nam', '2008-06-12', '0923149812', 'nguyenvanphuc46@gmail.com', 'nguyenvanphuc46', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 4, 3, null, null, 0, 7),
(N'Lê Ngọc Hân', N'Nữ', '2007-08-24', '0981238427', 'lengochan47@gmail.com', 'lengochan47', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 1, 3, null, null, 0, 35),
(N'Trịnh Hữu Dũng', N'Nam', '2007-01-30', '0938722134', 'trinhhuudung48@gmail.com', 'trinhhuudung48', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 5, 3, null, null, 0, 20),
(N'Vũ Thanh Tùng', N'Nam', '2009-11-12', '0923567489', 'vuthanhtung49@gmail.com', 'vuthanhtung49', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 3, 3, null, null, 0, 16),
(N'Phan Thị Mỹ', N'Nữ', '2008-04-06', '0893432244', 'phanthimy50@gmail.com', 'phanthimy50', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 1, 3, null, null, 0, 2),
(N'Ngô Hữu Lộc', N'Nam', '2007-09-14', '0921234321', 'ngohuuloc51@gmail.com', 'ngohuuloc51', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 5, 3, null, null, 0, 29),
(N'Nguyễn Ngọc Hân', N'Nữ', '2009-06-20', '0871732123', 'nguyenngochan52@gmail.com', 'nguyenngochan52', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 14, 3, null, null, 0, 36),
(N'Đỗ Văn Long', N'Nam', '2007-07-01', '0909876543', 'dovanlong53@gmail.com', 'dovanlong53', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 5, 3, null, null, 0, 30),
(N'Vũ Thị Hà', N'Nữ', '2009-02-28', '0912121212', 'vuthiha54@gmail.com', 'vuthiha54', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 2, 3, null, null, 0, 23),
(N'Phạm Hồng Quân', N'Nam', '2007-04-18', '0989012345', 'phamhongquan55@gmail.com', 'phamhongquan55', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 3, 3, null, null, 0, 26),
(N'Nguyễn Văn Đại', N'Nam', '2009-09-19', '0933333333', 'nguyenvandai56@gmail.com', 'nguyenvandai56', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 15, 3, null, null, 0, 38),
(N'Lê Thị Hằng', N'Nữ', '2008-05-11', '0923456723', 'lethihang57@gmail.com', 'lethihang57', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 1, 3, null, null, 0, 16),
(N'Trần Văn Phước', N'Nam', '2007-11-05', '0898888888', 'tranvanphuoc58@gmail.com', 'tranvanphuoc58', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 1, 3, null, null, 0, 11),
(N'Phạm Thị Tuyết', N'Nữ', '2008-12-25', '0925555555', 'phamthituyet59@gmail.com', 'phamthituyet59', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 2, 3, null, null, 0, 14),
(N'Ngô Minh Khôi', N'Nam', '2007-06-06', '0901234567', 'ngominhkhoi60@gmail.com', 'ngominhkhoi60', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 3, 3, null, null, 0, 15),
(N'Nguyễn Ngọc Minh', N'Nữ', '2007-06-14', '0896285114', 'nguyenngocminh61@gmail.com', 'nguyenngocminh61', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 14, 3, null, null, 0, 37),
(N'Trần Thị Bích', N'Nữ', '2008-08-08', '0896758925', 'tranthibich62@gmail.com', 'tranthibich62', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 4, 3, null, null, 0, 18),
(N'Hoàng Thị Thủy', N'Nữ', '2008-12-23', '0915407783', 'hoangthithuy63@gmail.com', 'hoangthithuy63', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 1, 3, null, null, 0, 12),
(N'Nguyễn Văn Duy', N'Nam', '2007-09-01', '0923315798', 'nguyenvanduy64@gmail.com', 'nguyenvanduy64', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 3, 3, null, null, 0, 5),
(N'Phạm Văn Hưng', N'Nam', '2009-06-03', '0873361606', 'phamvanhung65@gmail.com', 'phamvanhung65', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 3, 3, null, null, 0, 6),
(N'Vũ Thị Minh', N'Nữ', '2009-09-08', '0872964149', 'vuthiminh66@gmail.com', 'vuthiminh66', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 2, 3, null, null, 0, 3),
(N'Hồ Văn Tuấn', N'Nam', '2008-03-14', '0885692251', 'hovantuan67@gmail.com', 'hovantuan67', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 5, 3, null, null, 0, 10),
(N'Phan Thị Tâm', N'Nữ', '2008-10-04', '0938020756', 'phanthitam68@gmail.com', 'phanthitam68', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 4, 3, null, null, 0, 8),
(N'Trần Văn Khánh', N'Nam', '2007-02-27', '0878985539', 'tranvankhanh69@gmail.com', 'tranvankhanh69', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 2, 3, null, null, 0, 3),
(N'Lê Thị Duyên', N'Nữ', '2007-10-20', '0920788992', 'lethiduyen70@gmail.com', 'lethiduyen70', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 5, 3, null, null, 0, 10),
(N'Nguyễn Minh Châu', N'Nữ', '2008-05-15', '0904701013', 'nguyenminhchau71@gmail.com', 'nguyenminhchau71', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 2, 3, null, null, 0, 23),
(N'Hoàng Văn Dũng', N'Nam', '2008-02-04', '0871944136', 'hoangvandung72@gmail.com', 'hoangvandung72', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 3, 3, null, null, 0, 26),
(N'Nguyễn Ngọc Mai', N'Nữ', '2008-12-08', '0937632765', 'nguyenngocmai73@gmail.com', 'nguyenngocmai73', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 5, 3, null, null, 0, 29),
(N'Võ Văn Minh', N'Nam', '2008-07-17', '0921689822', 'vovanminh74@gmail.com', 'vovanminh74', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 14, 3, null, null, 0, 36),
(N'Trần Thị Kim', N'Nữ', '2009-03-18', '0894012567', 'tranthikim75@gmail.com', 'tranthikim75', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 5, 3, null, null, 0, 20),
(N'Ngô Văn Khôi', N'Nam', '2009-01-27', '0871871676', 'ngovankhoi76@gmail.com', 'ngovankhoi76', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 1, 3, null, null, 0, 35),
(N'Phạm Thị Vân', N'Nữ', '2007-06-30', '0936223439', 'phamthivan77@gmail.com', 'phamthivan77', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 1, 3, null, null, 0, 35),
(N'Nguyễn Thanh Tú', N'Nữ', '2008-11-14', '0881340197', 'nguyenthanhtu78@gmail.com', 'nguyenthanhtu78', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 2, 3, null, null, 0, 23),
(N'Hoàng Văn Tú', N'Nam', '2008-05-24', '0934606203', 'hoangvantu79@gmail.com', 'hoangvantu79', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 14, 3, null, null, 0, 36),
(N'Nguyễn Thị Hoa', N'Nữ', '2009-08-15', '0900540822', 'nguyenthihoa80@gmail.com', 'nguyenthihoa80', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 1, 3, null, null, 0, 11),
(N'Trần Văn Kiên', N'Nam', '2008-03-10', '0912584613', 'tranvankien81@gmail.com', 'tranvankien81', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 5, 3, null, null, 0, 29),
(N'Lê Thị Bích', N'Nữ', '2007-11-19', '0895043786', 'lethibich82@gmail.com', 'lethibich82', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 1, 3, null, null, 0, 21),
(N'Nguyễn Văn Cường', N'Nam', '2009-04-28', '0903969247', 'nguyenvancuong83@gmail.com', 'nguyenvancuong83', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 2, 3, null, null, 0, 14),
(N'Phạm Thị Dung', N'Nữ', '2008-06-01', '0889093185', 'phamthidung84@gmail.com', 'phamthidung84', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 4, 3, null, null, 0, 18),
(N'Hoàng Minh Tuấn', N'Nam', '2008-01-07', '0923800978', 'hoangminhtuan85@gmail.com', 'hoangminhtuan85', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 5, 3, null, null, 0, 20),
(N'Nguyễn Thị Hà', N'Nữ', '2007-12-30', '0878471837', 'nguyenthihà86@gmail.com', 'nguyenthihà86', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 1, 3, null, null, 0, 22),
(N'Lê Văn Nam', N'Nam', '2009-07-25', '0873302663', 'levannam87@gmail.com', 'levannam87', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 3, 3, null, null, 0, 26),
(N'Trần Thị Hồng', N'Nữ', '2007-05-05', '0908673599', 'tranthihong88@gmail.com', 'tranthihong88', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 2, 3, null, null, 0, 13),
(N'Vũ Văn Dương', N'Nam', '2008-08-20', '0934222870', 'vuvanduong89@gmail.com', 'vuvanduong89', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 4, 3, null, null, 0, 17),
(N'Phạm Thị Mai', N'Nữ', '2008-10-30', '0876464086', 'phamthimai90@gmail.com', 'phamthimai90', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 4, 3, null, null, 0, 18),
(N'Nguyễn Văn Bình', N'Nam', '2009-02-01', '0901234522', 'nguyenvanbinh91@gmail.com', 'nguyenvanbinh91', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 5, 3, null, null, 0, 10),
(N'Lê Thị Hương', N'Nữ', '2007-06-12', '0919653277', 'lethihuong92@gmail.com', 'lethihuong92', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 4, 3, null, null, 0, 28),
(N'Trần Văn Toàn', N'Nam', '2008-12-11', '0937938101', 'tranvantoan93@gmail.com', 'tranvantoan93', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 3, 3, null, null, 0, 25),
(N'Nguyễn Thị Quỳnh', N'Nữ', '2007-04-14', '0898042500', 'nguyenthiquynh94@gmail.com', 'nguyenthiquynh94', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 1, 3, null, null, 0, 11),
(N'Vũ Văn Thành', N'Nam', '2009-01-20', '0907071996', 'vuvanthanh95@gmail.com', 'vuvanthanh95', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 2, 3, null, null, 0, 13),
(N'Trần Thị Ngọc', N'Nữ', '2008-09-09', '0887426257', 'tranthingoc96@gmail.com', 'tranthingoc96', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 2, 3, null, null, 0, 13),
(N'Nguyễn Văn Khải', N'Nam', '2008-03-30', '0897356548', 'nguyenvankhai97@gmail.com', 'nguyenvankhai97', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 1, 3, null, null, 0, 1),
(N'Phạm Thị Lý', N'Nữ', '2009-06-05', '0938666509', 'phamthily98@gmail.com', 'phamthily98', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 1, 3, null, null, 0, 21),
(N'Lê Văn Trường', N'Nam', '2007-07-15', '0885509283', 'levantruong99@gmail.com', 'levantruong99', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 4, 3, null, null, 0, 17),
(N'Trần Thị Thu', N'Nữ', '2007-08-22', '0872442567', 'tranthithu100@gmail.com', 'tranthithu100', '/WebApplication3/image-loader/avatamacdinh.jpg', null, null, 2, 3, null, null, 0, 14)
;
