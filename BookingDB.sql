USE [master]
GO
/****** Object:  Database [BookingDB]    Script Date: 12/10/2019 11:39:47 AM ******/
CREATE DATABASE [BookingDB]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'BookingDB', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL14.MSSQLSERVER\MSSQL\DATA\BookingDB.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'BookingDB_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL14.MSSQLSERVER\MSSQL\DATA\BookingDB_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
GO
ALTER DATABASE [BookingDB] SET COMPATIBILITY_LEVEL = 140
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [BookingDB].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [BookingDB] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [BookingDB] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [BookingDB] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [BookingDB] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [BookingDB] SET ARITHABORT OFF 
GO
ALTER DATABASE [BookingDB] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [BookingDB] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [BookingDB] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [BookingDB] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [BookingDB] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [BookingDB] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [BookingDB] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [BookingDB] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [BookingDB] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [BookingDB] SET  DISABLE_BROKER 
GO
ALTER DATABASE [BookingDB] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [BookingDB] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [BookingDB] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [BookingDB] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [BookingDB] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [BookingDB] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [BookingDB] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [BookingDB] SET RECOVERY FULL 
GO
ALTER DATABASE [BookingDB] SET  MULTI_USER 
GO
ALTER DATABASE [BookingDB] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [BookingDB] SET DB_CHAINING OFF 
GO
ALTER DATABASE [BookingDB] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [BookingDB] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [BookingDB] SET DELAYED_DURABILITY = DISABLED 
GO
EXEC sys.sp_db_vardecimal_storage_format N'BookingDB', N'ON'
GO
ALTER DATABASE [BookingDB] SET QUERY_STORE = OFF
GO
USE [BookingDB]
GO
/****** Object:  Table [dbo].[Account]    Script Date: 12/10/2019 11:39:47 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Account](
	[acc_email] [nvarchar](60) NOT NULL,
	[acc_password] [nvarchar](50) NULL,
	[acc_address] [nvarchar](200) NULL,
	[acc_phone] [nvarchar](10) NULL,
	[create_date] [datetime] NULL,
	[status] [bit] NULL,
	[role_id] [int] NULL,
 CONSTRAINT [PK_Account] PRIMARY KEY CLUSTERED 
(
	[acc_email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Area]    Script Date: 12/10/2019 11:39:47 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Area](
	[area_id] [int] NOT NULL,
	[area_name] [nvarchar](50) NULL,
 CONSTRAINT [PK_Area] PRIMARY KEY CLUSTERED 
(
	[area_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Booking]    Script Date: 12/10/2019 11:39:47 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Booking](
	[booking_id] [int] IDENTITY(1,1) NOT NULL,
	[acc_email] [nvarchar](60) NULL,
	[booking_date] [datetime] NULL,
	[total_price] [float] NULL,
	[booking_discount] [float] NULL,
	[booking_status] [bit] NULL,
	[discount_id] [nvarchar](20) NULL,
 CONSTRAINT [PK_Booking] PRIMARY KEY CLUSTERED 
(
	[booking_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[BookingRoom]    Script Date: 12/10/2019 11:39:47 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BookingRoom](
	[booking_id] [int] NOT NULL,
	[type_id] [nvarchar](100) NOT NULL,
	[amount] [int] NULL,
	[price] [float] NULL,
	[date_checkin] [date] NULL,
	[date_checkout] [date] NULL,
 CONSTRAINT [PK_BookingRoom] PRIMARY KEY CLUSTERED 
(
	[booking_id] ASC,
	[type_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Discount]    Script Date: 12/10/2019 11:39:47 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Discount](
	[discount_id] [nvarchar](20) NOT NULL,
	[discount_value] [float] NULL,
	[status] [bit] NULL,
	[expiry_date] [date] NULL,
 CONSTRAINT [PK_Discount] PRIMARY KEY CLUSTERED 
(
	[discount_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Feedback]    Script Date: 12/10/2019 11:39:47 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Feedback](
	[hotel_id] [nvarchar](50) NULL,
	[acc_email] [nvarchar](60) NULL,
	[feedback_content] [nvarchar](max) NULL,
	[feedback_date] [datetime] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Hotel]    Script Date: 12/10/2019 11:39:47 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Hotel](
	[hotel_id] [nvarchar](50) NOT NULL,
	[hotel_name] [nvarchar](100) NULL,
	[hotel_address] [nvarchar](200) NULL,
	[hotel_phone] [nvarchar](10) NULL,
	[hotel_photo] [nvarchar](max) NULL,
	[area_id] [int] NULL,
	[hotel_status] [bit] NULL,
	[description] [nvarchar](1000) NULL,
 CONSTRAINT [PK_Hotel] PRIMARY KEY CLUSTERED 
(
	[hotel_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Role]    Script Date: 12/10/2019 11:39:47 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Role](
	[role_id] [int] NOT NULL,
	[role_name] [nvarchar](10) NULL,
 CONSTRAINT [PK_Role] PRIMARY KEY CLUSTERED 
(
	[role_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[RoomType]    Script Date: 12/10/2019 11:39:47 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[RoomType](
	[type_id] [nvarchar](100) NOT NULL,
	[hotel_id] [nvarchar](50) NULL,
	[type_name] [nvarchar](50) NULL,
	[type_price] [float] NULL,
	[type_amount] [int] NULL,
 CONSTRAINT [PK_RoomType] PRIMARY KEY CLUSTERED 
(
	[type_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
INSERT [dbo].[Account] ([acc_email], [acc_password], [acc_address], [acc_phone], [create_date], [status], [role_id]) VALUES (N'customer@gmail.com', N'123', NULL, NULL, CAST(N'2019-12-08T15:07:53.043' AS DateTime), 1, 1)
INSERT [dbo].[Account] ([acc_email], [acc_password], [acc_address], [acc_phone], [create_date], [status], [role_id]) VALUES (N'haha@haha', N'123', N'123', N'0775615772', CAST(N'2019-12-09T20:25:33.870' AS DateTime), 1, 1)
INSERT [dbo].[Account] ([acc_email], [acc_password], [acc_address], [acc_phone], [create_date], [status], [role_id]) VALUES (N'sang@cute', N'123', N'123 afsdf', N'1234567890', CAST(N'2019-12-09T20:05:06.577' AS DateTime), 1, 1)
INSERT [dbo].[Account] ([acc_email], [acc_password], [acc_address], [acc_phone], [create_date], [status], [role_id]) VALUES (N'sangnvse130361@fpt.edu.vn', N'123', N'asd', N'0775615772', CAST(N'2019-12-10T10:55:19.840' AS DateTime), 1, 1)
INSERT [dbo].[Account] ([acc_email], [acc_password], [acc_address], [acc_phone], [create_date], [status], [role_id]) VALUES (N'shang2856@gmail.com', N'123', N'259 Hong Bang phuong 11, quan 5', N'0775615772', CAST(N'2019-12-04T01:12:46.300' AS DateTime), 1, 0)
INSERT [dbo].[Area] ([area_id], [area_name]) VALUES (10, N'BENTRE')
INSERT [dbo].[Area] ([area_id], [area_name]) VALUES (12, N'BINHDINH')
INSERT [dbo].[Area] ([area_id], [area_name]) VALUES (7, N'CAMAU')
INSERT [dbo].[Area] ([area_id], [area_name]) VALUES (9, N'CANTHO')
INSERT [dbo].[Area] ([area_id], [area_name]) VALUES (13, N'DAKLAK')
INSERT [dbo].[Area] ([area_id], [area_name]) VALUES (5, N'DALAT')
INSERT [dbo].[Area] ([area_id], [area_name]) VALUES (11, N'DONGTHAP')
INSERT [dbo].[Area] ([area_id], [area_name]) VALUES (2, N'HANOI')
INSERT [dbo].[Area] ([area_id], [area_name]) VALUES (1, N'HOCHIMINH')
INSERT [dbo].[Area] ([area_id], [area_name]) VALUES (6, N'HUE')
INSERT [dbo].[Area] ([area_id], [area_name]) VALUES (4, N'NHATRANG')
INSERT [dbo].[Area] ([area_id], [area_name]) VALUES (8, N'SAPA')
INSERT [dbo].[Area] ([area_id], [area_name]) VALUES (3, N'VUNGTAU')
SET IDENTITY_INSERT [dbo].[Booking] ON 

INSERT [dbo].[Booking] ([booking_id], [acc_email], [booking_date], [total_price], [booking_discount], [booking_status], [discount_id]) VALUES (1, N'shang2856@gmail.com', CAST(N'2019-12-04T15:01:35.020' AS DateTime), 100, 0, 0, NULL)
INSERT [dbo].[Booking] ([booking_id], [acc_email], [booking_date], [total_price], [booking_discount], [booking_status], [discount_id]) VALUES (6, N'shang2856@gmail.com', CAST(N'2019-12-04T15:15:27.680' AS DateTime), 30, 0, 0, NULL)
INSERT [dbo].[Booking] ([booking_id], [acc_email], [booking_date], [total_price], [booking_discount], [booking_status], [discount_id]) VALUES (7, N'shang2856@gmail.com', CAST(N'2019-12-05T09:23:46.490' AS DateTime), 0, 0, 0, NULL)
INSERT [dbo].[Booking] ([booking_id], [acc_email], [booking_date], [total_price], [booking_discount], [booking_status], [discount_id]) VALUES (9, N'customer@gmail.com', CAST(N'2019-12-09T00:39:00.083' AS DateTime), NULL, 0, 0, NULL)
INSERT [dbo].[Booking] ([booking_id], [acc_email], [booking_date], [total_price], [booking_discount], [booking_status], [discount_id]) VALUES (12, N'customer@gmail.com', CAST(N'2019-12-09T03:12:42.333' AS DateTime), 2200, 0, 0, NULL)
INSERT [dbo].[Booking] ([booking_id], [acc_email], [booking_date], [total_price], [booking_discount], [booking_status], [discount_id]) VALUES (13, N'customer@gmail.com', CAST(N'2019-12-09T03:13:15.597' AS DateTime), 4800, 0, 0, NULL)
INSERT [dbo].[Booking] ([booking_id], [acc_email], [booking_date], [total_price], [booking_discount], [booking_status], [discount_id]) VALUES (14, N'customer@gmail.com', CAST(N'2019-12-09T07:55:07.123' AS DateTime), 800, 0, 0, NULL)
INSERT [dbo].[Booking] ([booking_id], [acc_email], [booking_date], [total_price], [booking_discount], [booking_status], [discount_id]) VALUES (15, N'customer@gmail.com', CAST(N'2019-12-09T07:56:03.460' AS DateTime), 600, 0, 0, NULL)
INSERT [dbo].[Booking] ([booking_id], [acc_email], [booking_date], [total_price], [booking_discount], [booking_status], [discount_id]) VALUES (16, N'customer@gmail.com', CAST(N'2019-12-09T08:43:58.577' AS DateTime), 800, 0, 1, NULL)
INSERT [dbo].[Booking] ([booking_id], [acc_email], [booking_date], [total_price], [booking_discount], [booking_status], [discount_id]) VALUES (17, N'customer@gmail.com', CAST(N'2019-12-09T14:02:01.180' AS DateTime), 500, 0, 1, NULL)
INSERT [dbo].[Booking] ([booking_id], [acc_email], [booking_date], [total_price], [booking_discount], [booking_status], [discount_id]) VALUES (18, N'customer@gmail.com', CAST(N'2019-12-09T14:40:32.953' AS DateTime), 7800, 0, 1, NULL)
INSERT [dbo].[Booking] ([booking_id], [acc_email], [booking_date], [total_price], [booking_discount], [booking_status], [discount_id]) VALUES (19, N'sang@cute', CAST(N'2019-12-09T20:18:32.380' AS DateTime), 1200, 0, 1, NULL)
INSERT [dbo].[Booking] ([booking_id], [acc_email], [booking_date], [total_price], [booking_discount], [booking_status], [discount_id]) VALUES (20, N'customer@gmail.com', CAST(N'2019-12-09T22:17:22.840' AS DateTime), 1600, 0.5, 1, N'DISCOUNT2')
INSERT [dbo].[Booking] ([booking_id], [acc_email], [booking_date], [total_price], [booking_discount], [booking_status], [discount_id]) VALUES (21, N'customer@gmail.com', CAST(N'2019-12-09T22:20:40.907' AS DateTime), 6800, 0.5, 1, N'DISCOUNT5')
INSERT [dbo].[Booking] ([booking_id], [acc_email], [booking_date], [total_price], [booking_discount], [booking_status], [discount_id]) VALUES (22, N'sang@cute', CAST(N'2019-12-10T09:09:45.973' AS DateTime), 400, 0, 1, NULL)
INSERT [dbo].[Booking] ([booking_id], [acc_email], [booking_date], [total_price], [booking_discount], [booking_status], [discount_id]) VALUES (23, N'sangnvse130361@fpt.edu.vn', CAST(N'2019-12-10T11:37:04.537' AS DateTime), 250, 0, 1, NULL)
SET IDENTITY_INSERT [dbo].[Booking] OFF
INSERT [dbo].[BookingRoom] ([booking_id], [type_id], [amount], [price], [date_checkin], [date_checkout]) VALUES (14, N'HOTEL-1_1', 3, 200, CAST(N'2019-12-09' AS Date), CAST(N'2019-12-11' AS Date))
INSERT [dbo].[BookingRoom] ([booking_id], [type_id], [amount], [price], [date_checkin], [date_checkout]) VALUES (14, N'HOTEL-1_2', 1, 400, CAST(N'2019-12-09' AS Date), CAST(N'2019-12-11' AS Date))
INSERT [dbo].[BookingRoom] ([booking_id], [type_id], [amount], [price], [date_checkin], [date_checkout]) VALUES (16, N'HOTEL-1_1', 2, 200, CAST(N'2019-12-09' AS Date), CAST(N'2019-12-11' AS Date))
INSERT [dbo].[BookingRoom] ([booking_id], [type_id], [amount], [price], [date_checkin], [date_checkout]) VALUES (16, N'HOTEL-1_2', 2, 400, CAST(N'2019-12-09' AS Date), CAST(N'2019-12-11' AS Date))
INSERT [dbo].[BookingRoom] ([booking_id], [type_id], [amount], [price], [date_checkin], [date_checkout]) VALUES (17, N'HOTEL-2_2', 1, 500, CAST(N'2019-12-09' AS Date), CAST(N'2019-12-10' AS Date))
INSERT [dbo].[BookingRoom] ([booking_id], [type_id], [amount], [price], [date_checkin], [date_checkout]) VALUES (18, N'HOTEL-1_3', 3, 800, CAST(N'2019-12-09' AS Date), CAST(N'2019-12-11' AS Date))
INSERT [dbo].[BookingRoom] ([booking_id], [type_id], [amount], [price], [date_checkin], [date_checkout]) VALUES (18, N'HOTEL-2_2', 2, 500, CAST(N'2019-12-10' AS Date), CAST(N'2019-12-13' AS Date))
INSERT [dbo].[BookingRoom] ([booking_id], [type_id], [amount], [price], [date_checkin], [date_checkout]) VALUES (19, N'HOTEL-1_1', 2, 200, CAST(N'2019-12-09' AS Date), CAST(N'2019-12-12' AS Date))
INSERT [dbo].[BookingRoom] ([booking_id], [type_id], [amount], [price], [date_checkin], [date_checkout]) VALUES (20, N'HOTEL-1_2', 2, 400, CAST(N'2019-12-09' AS Date), CAST(N'2019-12-10' AS Date))
INSERT [dbo].[BookingRoom] ([booking_id], [type_id], [amount], [price], [date_checkin], [date_checkout]) VALUES (20, N'HOTEL-1_3', 1, 800, CAST(N'2019-12-09' AS Date), CAST(N'2019-12-10' AS Date))
INSERT [dbo].[BookingRoom] ([booking_id], [type_id], [amount], [price], [date_checkin], [date_checkout]) VALUES (21, N'HOTEL-1_1', 1, 200, CAST(N'2019-12-19' AS Date), CAST(N'2019-12-31' AS Date))
INSERT [dbo].[BookingRoom] ([booking_id], [type_id], [amount], [price], [date_checkin], [date_checkout]) VALUES (21, N'HOTEL-1_2', 1, 400, CAST(N'2019-12-19' AS Date), CAST(N'2019-12-30' AS Date))
INSERT [dbo].[BookingRoom] ([booking_id], [type_id], [amount], [price], [date_checkin], [date_checkout]) VALUES (22, N'HOTEL-1_1', 2, 200, CAST(N'2019-12-10' AS Date), CAST(N'2019-12-11' AS Date))
INSERT [dbo].[BookingRoom] ([booking_id], [type_id], [amount], [price], [date_checkin], [date_checkout]) VALUES (23, N'HOTEL-2_1', 1, 250, CAST(N'2019-12-10' AS Date), CAST(N'2019-12-11' AS Date))
INSERT [dbo].[Discount] ([discount_id], [discount_value], [status], [expiry_date]) VALUES (N'DISCOUNT1', 0.5, 1, CAST(N'2019-12-05' AS Date))
INSERT [dbo].[Discount] ([discount_id], [discount_value], [status], [expiry_date]) VALUES (N'DISCOUNT2', 0.5, 0, CAST(N'2019-12-19' AS Date))
INSERT [dbo].[Discount] ([discount_id], [discount_value], [status], [expiry_date]) VALUES (N'DISCOUNT3', 0.5, 1, CAST(N'2019-12-08' AS Date))
INSERT [dbo].[Discount] ([discount_id], [discount_value], [status], [expiry_date]) VALUES (N'DISCOUNT4', 0.2, 1, CAST(N'2019-12-10' AS Date))
INSERT [dbo].[Discount] ([discount_id], [discount_value], [status], [expiry_date]) VALUES (N'DISCOUNT5', 0.5, 0, CAST(N'2020-12-10' AS Date))
INSERT [dbo].[Discount] ([discount_id], [discount_value], [status], [expiry_date]) VALUES (N'DISCOUNT6', 0.5, 1, CAST(N'2020-12-10' AS Date))
INSERT [dbo].[Discount] ([discount_id], [discount_value], [status], [expiry_date]) VALUES (N'SANGNE', 0.5, 1, CAST(N'2019-12-08' AS Date))
INSERT [dbo].[Feedback] ([hotel_id], [acc_email], [feedback_content], [feedback_date]) VALUES (N'HOTEL-1', N'customer@gmail.com', N'Chan ghe
', CAST(N'2019-12-09T17:25:17.477' AS DateTime))
INSERT [dbo].[Feedback] ([hotel_id], [acc_email], [feedback_content], [feedback_date]) VALUES (N'HOTEL-1', N'customer@gmail.com', N'Chan ghe
', CAST(N'2019-12-09T17:26:12.393' AS DateTime))
INSERT [dbo].[Feedback] ([hotel_id], [acc_email], [feedback_content], [feedback_date]) VALUES (N'HOTEL-2', N'customer@gmail.com', N'Hoi dep a nha
', CAST(N'2019-12-09T17:26:26.873' AS DateTime))
INSERT [dbo].[Feedback] ([hotel_id], [acc_email], [feedback_content], [feedback_date]) VALUES (N'HOTEL-2', N'customer@gmail.com', N'Hihi', CAST(N'2019-12-09T18:07:28.610' AS DateTime))
INSERT [dbo].[Hotel] ([hotel_id], [hotel_name], [hotel_address], [hotel_phone], [hotel_photo], [area_id], [hotel_status], [description]) VALUES (N'HOTEL-1', N'Hotel Rose Palace', N'123 Nguyen Trai, phuong 5 quan 11', N'1234567890', N'aaa.jpg', 1, 1, N'Situated in Ho Chi Minh City in the Ho Chi Minh Municipality region, with Tan Dinh Market and Diamond Plaza nearby, Estelle Boutique Apartment - Pink Cathedral features accommodation with free WiFi and free private parking.

There is a fully equipped kitchenette and a private bathroom.

The aparthotel provides a laundry service, as well as business facilities like fax and photocopying.

Estelle Boutique Apartment - Pink provides a terrace.

Popular points of interest near the accommodation include War Remnants Museum, Saigon Central Post Office and Reunification Palace. The nearest airport is Tan Son Nhat International, 11 km from Estelle Boutique Apartment - Pink Cathedral, and the property offers a paid airport shuttle service.

District 1 is a great choice for travellers interested in museums, shopping and history.')
INSERT [dbo].[Hotel] ([hotel_id], [hotel_name], [hotel_address], [hotel_phone], [hotel_photo], [area_id], [hotel_status], [description]) VALUES (N'HOTEL-2', N'Hotel Grand Palace', N'111 Hai Thuong Lan Ong ', N'1234567890', N'bbb.jpg', 2, 1, N'Boasting an outdoor swimming pool and a bar, Icon Saigon - Luxury Design Hotel is set in Ho Chi Minh City, 300 m from Saigon Opera House and 500 m from Vincom Shopping Center. Among the facilities of this property are a restaurant, a 24-hour front desk and room service, along with free WiFi. The property is close to popular attractions like Saigon Notre Dame Cathedral, Diamond Plaza and Takashimaya Vietnam.

A continental breakfast is available every morning at the hotel.

Popular points of interest near Icon Saigon - Luxury Design Hotel include Union Square Saigon Shopping Mall, Ho Chi Minh City Hall and Saigon Central Post Office. The nearest airport is Tan Son Nhat International Airport, 13 km from the accommodation.

District 1 is a great choice for travellers interested in museums, shopping and history.')
INSERT [dbo].[Hotel] ([hotel_id], [hotel_name], [hotel_address], [hotel_phone], [hotel_photo], [area_id], [hotel_status], [description]) VALUES (N'HOTEL-3', N'Hotel Golden Host', N'9999 Ben Tre', N'123456789', N'aaa.jpg', 10, 1, N'Boasting an outdoor swimming pool and a bar, Icon Saigon - Luxury Design Hotel is set in Ho Chi Minh City, 300 m from Saigon Opera House and 500 m from Vincom Shopping Center. Among the facilities of this property are a restaurant, a 24-hour front desk and room service, along with free WiFi. The property is close to popular attractions like Saigon Notre Dame Cathedral, Diamond Plaza and Takashimaya Vietnam.

A continental breakfast is available every morning at the hotel.

Popular points of interest near Icon Saigon - Luxury Design Hotel include Union Square Saigon Shopping Mall, Ho Chi Minh City Hall and Saigon Central Post Office. The nearest airport is Tan Son Nhat International Airport, 13 km from the accommodation.

District 1 is a great choice for travellers interested in museums, shopping and history.')
INSERT [dbo].[Hotel] ([hotel_id], [hotel_name], [hotel_address], [hotel_phone], [hotel_photo], [area_id], [hotel_status], [description]) VALUES (N'HOTEL-4', N'Hotel Sang Kute', N'123 abc', N'123456789', N'bbb.jpg', 3, 1, N'Situated in Ho Chi Minh City in the Ho Chi Minh Municipality region, with Tan Dinh Market and Diamond Plaza nearby, Estelle Boutique Apartment - Pink Cathedral features accommodation with free WiFi and free private parking.

There is a fully equipped kitchenette and a private bathroom.

The aparthotel provides a laundry service, as well as business facilities like fax and photocopying.

Estelle Boutique Apartment - Pink provides a terrace.

Popular points of interest near the accommodation include War Remnants Museum, Saigon Central Post Office and Reunification Palace. The nearest airport is Tan Son Nhat International, 11 km from Estelle Boutique Apartment - Pink Cathedral, and the property offers a paid airport shuttle service.

District 1 is a great choice for travellers interested in museums, shopping and history.')
INSERT [dbo].[Role] ([role_id], [role_name]) VALUES (0, N'ADMIN')
INSERT [dbo].[Role] ([role_id], [role_name]) VALUES (1, N'CUSTOMER')
INSERT [dbo].[RoomType] ([type_id], [hotel_id], [type_name], [type_price], [type_amount]) VALUES (N'HOTEL-1_1', N'HOTEL-1', N'Single', 200, 5)
INSERT [dbo].[RoomType] ([type_id], [hotel_id], [type_name], [type_price], [type_amount]) VALUES (N'HOTEL-1_2', N'HOTEL-1', N'Double', 400, 5)
INSERT [dbo].[RoomType] ([type_id], [hotel_id], [type_name], [type_price], [type_amount]) VALUES (N'HOTEL-1_3', N'HOTEL-1', N'Family', 800, 5)
INSERT [dbo].[RoomType] ([type_id], [hotel_id], [type_name], [type_price], [type_amount]) VALUES (N'HOTEL-2_1', N'HOTEL-2', N'Single', 250, 5)
INSERT [dbo].[RoomType] ([type_id], [hotel_id], [type_name], [type_price], [type_amount]) VALUES (N'HOTEL-2_2', N'HOTEL-2', N'Double', 500, 5)
INSERT [dbo].[RoomType] ([type_id], [hotel_id], [type_name], [type_price], [type_amount]) VALUES (N'HOTEL-3_1', N'HOTEL-3', N'Normal', 100, 10)
INSERT [dbo].[RoomType] ([type_id], [hotel_id], [type_name], [type_price], [type_amount]) VALUES (N'HOTEL-3_2', N'HOTEL-3', N'Luxury', 250, 10)
INSERT [dbo].[RoomType] ([type_id], [hotel_id], [type_name], [type_price], [type_amount]) VALUES (N'HOTEL-3_3', N'HOTEL-3', N'Golden', 500, 5)
SET ANSI_PADDING ON
GO
/****** Object:  Index [IX_Area]    Script Date: 12/10/2019 11:39:47 AM ******/
ALTER TABLE [dbo].[Area] ADD  CONSTRAINT [IX_Area] UNIQUE NONCLUSTERED 
(
	[area_name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [IX_Role]    Script Date: 12/10/2019 11:39:47 AM ******/
ALTER TABLE [dbo].[Role] ADD  CONSTRAINT [IX_Role] UNIQUE NONCLUSTERED 
(
	[role_name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Account] ADD  CONSTRAINT [DF_Account_create_date]  DEFAULT (getdate()) FOR [create_date]
GO
ALTER TABLE [dbo].[Account] ADD  CONSTRAINT [DF_Account_status]  DEFAULT ((1)) FOR [status]
GO
ALTER TABLE [dbo].[Booking] ADD  CONSTRAINT [DF_Booking_booking_date]  DEFAULT (getdate()) FOR [booking_date]
GO
ALTER TABLE [dbo].[Booking] ADD  CONSTRAINT [DF_Booking_booking_status]  DEFAULT ((1)) FOR [booking_status]
GO
ALTER TABLE [dbo].[Feedback] ADD  CONSTRAINT [DF_Feedback_feedback_date]  DEFAULT (getdate()) FOR [feedback_date]
GO
ALTER TABLE [dbo].[Hotel] ADD  CONSTRAINT [DF_Hotel_hotel_status]  DEFAULT ((1)) FOR [hotel_status]
GO
ALTER TABLE [dbo].[Account]  WITH CHECK ADD  CONSTRAINT [FK_Account_Role] FOREIGN KEY([role_id])
REFERENCES [dbo].[Role] ([role_id])
GO
ALTER TABLE [dbo].[Account] CHECK CONSTRAINT [FK_Account_Role]
GO
ALTER TABLE [dbo].[Booking]  WITH CHECK ADD  CONSTRAINT [FK_Booking_Account] FOREIGN KEY([acc_email])
REFERENCES [dbo].[Account] ([acc_email])
GO
ALTER TABLE [dbo].[Booking] CHECK CONSTRAINT [FK_Booking_Account]
GO
ALTER TABLE [dbo].[Booking]  WITH CHECK ADD  CONSTRAINT [FK_Booking_Discount] FOREIGN KEY([discount_id])
REFERENCES [dbo].[Discount] ([discount_id])
GO
ALTER TABLE [dbo].[Booking] CHECK CONSTRAINT [FK_Booking_Discount]
GO
ALTER TABLE [dbo].[BookingRoom]  WITH CHECK ADD  CONSTRAINT [FK_BookingRoom_Booking] FOREIGN KEY([booking_id])
REFERENCES [dbo].[Booking] ([booking_id])
GO
ALTER TABLE [dbo].[BookingRoom] CHECK CONSTRAINT [FK_BookingRoom_Booking]
GO
ALTER TABLE [dbo].[BookingRoom]  WITH CHECK ADD  CONSTRAINT [FK_BookingRoom_RoomType] FOREIGN KEY([type_id])
REFERENCES [dbo].[RoomType] ([type_id])
GO
ALTER TABLE [dbo].[BookingRoom] CHECK CONSTRAINT [FK_BookingRoom_RoomType]
GO
ALTER TABLE [dbo].[Feedback]  WITH CHECK ADD  CONSTRAINT [FK_Feedback_Account] FOREIGN KEY([acc_email])
REFERENCES [dbo].[Account] ([acc_email])
GO
ALTER TABLE [dbo].[Feedback] CHECK CONSTRAINT [FK_Feedback_Account]
GO
ALTER TABLE [dbo].[Feedback]  WITH CHECK ADD  CONSTRAINT [FK_Feedback_Hotel] FOREIGN KEY([hotel_id])
REFERENCES [dbo].[Hotel] ([hotel_id])
GO
ALTER TABLE [dbo].[Feedback] CHECK CONSTRAINT [FK_Feedback_Hotel]
GO
ALTER TABLE [dbo].[Hotel]  WITH CHECK ADD  CONSTRAINT [FK_Hotel_Area] FOREIGN KEY([area_id])
REFERENCES [dbo].[Area] ([area_id])
GO
ALTER TABLE [dbo].[Hotel] CHECK CONSTRAINT [FK_Hotel_Area]
GO
ALTER TABLE [dbo].[RoomType]  WITH CHECK ADD  CONSTRAINT [FK_RoomType_Hotel] FOREIGN KEY([hotel_id])
REFERENCES [dbo].[Hotel] ([hotel_id])
GO
ALTER TABLE [dbo].[RoomType] CHECK CONSTRAINT [FK_RoomType_Hotel]
GO
USE [master]
GO
ALTER DATABASE [BookingDB] SET  READ_WRITE 
GO
