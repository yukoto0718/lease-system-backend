# 🏠 Rental Management System


[日本語版](./README.md)  | **English Version**

## 📋 Table of Contents

- [Project Background](#project-background)
- [Demo URLs](#demo-urls)
  - [Mobile App](#mobile-app)
  - [Admin Panel](#admin-panel)
- [Feature Modules](#feature-modules)
  - [Mobile App](#mobile-app-features)
  - [Admin Panel](#admin-panel-features)
- [System Architecture](#system-architecture)
- [Project Structure](#project-structure)
- [Environment Setup & Deployment](#environment-setup--deployment)
- [References](#references)

### Project Deployment - AWS EC2 Deployment Guide
[lease-docker-project](https://github.com/yukoto0718/lease-docker-project.git) 


## Project Background

**U+ Apartment** is a rental platform project developed as a secondary development based on the "Shangtinggongyu" online course from Silicon Valley. It consists of a mobile application for general users and an administrative management system for administrators.

- **Mobile App:** Designed for general users, providing features such as property search, viewing appointments, blog posting, likes, and private chat functionality.
- **Admin System:** Designed for administrators, equipped with apartment management, rental management, user management, and other administrative functions.

※ Images displayed in the documentation utilize Alibaba Cloud's Object Storage Service (OSS).

## Demo URLs
This demo site is deployed on AWS EC2 (t2.micro, 1-year free tier) and operates temporarily for portfolio demonstration purposes.

### Mobile App
Demo URL: https://uplus-lease.online

① Verification Code Login:
Log in using a verification code sent to your email address, and you will be automatically registered as a new user.

② Password Login:
User ①: `Username: 13112345678 Password: 123456`  
User ②: `Username: 13212345678 Password: 123456`  
User ③: `Username: 13312345678 Password: 123456`  

### Admin Panel
Demo URL: https://uplus-lease.online/admin
`Username: user Password: 123456` 

> **🔤 About Multi-language Support**  
> - **Mobile App:** **3-language support** (Chinese, Japanese, English) using Vue i18n
> - **Admin Panel:** **Chinese-to-Japanese translation** implemented using **translation scripts**
> 
> **📝 Note:** The Japanese display in the admin panel was generated through **batch translation processing** using a **translation script** ([chinese-to-japanese](https://github.com/yukoto0718/chinese-to-japanese.git)) **powered by Youdao API**. Therefore, there may be some unnatural expressions or translation accuracy issues. Please understand this as a technical implementation demonstration.

## Feature Modules

### Mobile App Features

**Main features are shown in the diagram below:**
![](https://sky-yu0718.oss-cn-beijing.aliyuncs.com/LeaseU8.png)

### Screen Overview

Details of each feature module are as follows:

- **Registration & Login Screen**

    ① Send a one-time code to your email address and log in by entering the code. Unregistered users are automatically registered when entering the code.
    ② Account/Password Login: Login is also available using ID and password method

  ![](https://sky-yu0718.oss-cn-beijing.aliyuncs.com/LeaseU2.png)  

- **Property Search**
  Rich search criteria: Filter by geographical location, rent range, payment methods (advance rent/monthly payment, etc.)
  Map integration: Utilize OpenStreetMap to specify areas on the map and search/display properties
  ![](https://sky-yu0718.oss-cn-beijing.aliyuncs.com/LeaseU3.png)  

**Example of i18n usage in database fields:**
`Table apartment_info: introduction_cn・introduction_en・introduction_ja`
  ![](https://sky-yu0718.oss-cn-beijing.aliyuncs.com/LeaseU4.png)  

- **My Page**

① Viewing Appointment Management: Select desired date/time and property for viewing appointments, with the ability to check, modify, and cancel appointment status

② Contract Management: Display current rental contract information in a list, submit contract termination (cancellation) or renewal requests through the app

③ Browsing History: Display previously checked properties in a list, quickly recall properties you want to review in detail again

④ Avatar & Nickname Editing: Freely change profile icon images and nicknames

  ![](https://sky-yu0718.oss-cn-beijing.aliyuncs.com/LeaseU5.png)  

- **Community Features**

① Blog Posting
・Post blog articles visible to all users
・Like (Redis) and comment functionality
・Comment deletion: Users can delete comments they posted or comments on their own blogs
・Tap on the poster's icon to navigate to that user's profile page

② Follow List
・Display lists of users you are "Following," "Followers," and "Mutual Follows"
・Unfollow operations and direct chat screen access by tapping icons

③ Private Chat
・Real-time bidirectional communication using WebSocket
・Display unread message count on the message list screen

  ![](https://sky-yu0718.oss-cn-beijing.aliyuncs.com/LeaseU6.png)  

### Admin Panel Features

> **🔧 About Admin Panel Implementation Scope**  
> For the admin panel of this project, we have implemented **translation support only**, and to support the i18n functionality of the mobile app, we have **modified only the "Example of i18n usage in database fields" screen**.
> In this README, we only include screenshots of the uniquely implemented parts.
> Other administrative functions **maintain the basic implemented features as-is**. If you wish to check the details of the features, please refer to:
> - 🌐 Check actual operations at [Demo URL](http://54.95.189.69:8888)
> - 📚 Check [References](#references)

**Main features are shown in the diagram below:**

* **Apartment Information Management**  
Manage basic information such as apartment names, addresses, and contact details. Provide apartment addition, editing, and deletion functions.

* **Room Information Management**  
Manage room details within each apartment (room number, layout, area, rent, etc.). Batch management of room information is available.

* **Property Attribute Management**  
Define various attributes (equipment, specifications, etc.) for apartments and rooms. Set attributes that administrators can select when registering property information.

* **Viewing Appointment Management**  
Manage viewing appointment requests from users. Confirm reservations from the mobile app and support staff schedule coordination.

* **Rental Contract Management**  
Manage creation, modification, and termination of rental contracts. Streamline contract transmission to users through contract generation functionality.

* **Administrator Account Management**  
Manage administrator accounts for the backend system. Provide account creation, editing, deletion, and deactivation functions.

* **User Management**  
Manage mobile app user information. Confirm user information and handle account-related issues.

**Example of i18n usage in database fields:**
  ![](https://sky-yu0718.oss-cn-beijing.aliyuncs.com/LeaseU7.png)  

## System Architecture

> **🔧 Background on Development Environment Configuration**  
> This project started with **traditional development on a CentOS7 virtual machine during the early learning stage**. We began learning by installing MySQL, Redis, and MinIO on the virtual machine and connecting from a Windows development environment.
> 
> Later, **when considering actual deployment, we recognized the advantages of Docker containerization** and **added Docker environment construction** on the virtual machine. This achieved a flexible development system that **maintains the traditional development environment while using a containerized test environment equivalent to production**.

**System Architecture is as follows:**
```
┌──────────────────────────────────────────────────────────────────────────────────────┐
│                        💻 Development Environment (Windows PC)                       │
├──────────────────────────────────────────────────────────────────────────────────────┤
│  ┌─────────────────────┐    ┌─────────────────────┐                                  │
│  │   IntelliJ IDEA     │    │      VSCode         │                                  │
│  │                     │    │                     │                                  │
│  │     SpringBoot      │    │     Vue3 Frontend   │                                  │
│  │     JDK 17          │    │     TypeScript      │                                  │
│  │     MyBatis Plus    │    │     Vite            │                                  │
│  └─────────────────────┘    └─────────────────────┘                                  │
│           └──────────┬────────────────┘                                              │
│                      ↓ (Connection)                                                  │
│  ┌─────────────────────────────────────────────────────────────────────────────────┐ │
│  │                   CentOS7 VM - Development Services                             │ │
│  │  ┌─────────────┐ ┌─────────────┐ ┌─────────────┐                                │ │
│  │  │   MySQL     │ │    Redis    │ │   MinIO     │                                │ │
│  │  └─────────────┘ └─────────────┘ └─────────────┘                                │ │
│  └─────────────────────────────────────────────────────────────────────────────────┘ │
└──────────────────────────────────────────────────────────────────────────────────────┘
↓ (JAR + Dist File Upload)
┌──────────────────────────────────────┼───────────────────────────────────────────────┐
│                                      │     🧪 Test Environment (CentOS7 VM)          │
├──────────────────────────────────────┼───────────────────────────────────────────────┤
│                                      ↓                                               │
│  ┌─────────────────────────────────────────────────────────────────────────────────┐ │
│  │                          🐳 Docker Compose Environment                          │ │
│  │ ┌────────┐ ┌────────┐ ┌────────┐ ┌─────────┐ ┌────────┐ ┌────────┐ ┌────────┐   │ │
│  │ │ nginx  │ │webadmin│ │ webapp │ │  mysql  │ │ redis  │ │ minio  │ │adminer │   │ │
│  │ └────────┘ └────────┘ └────────┘ └─────────┘ └────────┘ └────────┘ └────────┘   │ │
│  └─────────────────────────────────────────────────────────────────────────────────┘ │
└───────────────────────────────────────┼──────────────────────────────────────────────┘
↓ (Git Push)
┌───────────────────────────────────────┼─────────────────────────────────────────────┐
│                            📁 GitHub Repository                                     │
├───────────────────────────────────────┼─────────────────────────────────────────────┤
│  🔄 GitHub Actions (Automated Deployment)                                           │
└───────────────────────────────────────┼─────────────────────────────────────────────┘
↓
┌───────────────────────────────────────┼─────────────────────────────────────────────┐
│                    ☁️ AWS EC2 Production Environment (t2.micro)                     │
└─────────────────────────────────────────────────────────────────────────────────────┘
```

## Project Structure
```
lease-system-backend/
├── common  # Common module (general utilities, common configurations)
│   ├── pom.xml
│   └── src
├── model  # Data models
│   ├── pom.xml
│   └── src
├── web  # Web application layer
│   ├── pom.xml
│   ├── web-admin  # Admin panel web module
│   │   ├── pom.xml
│   │   └── src
│   └── web-app  # Mobile app web module
│       ├── pom.xml
│       └── src
└── pom.xml
```

## Environment Setup & Deployment
### ☁️ AWS EC2 Basic Configuration

#### EC2 Instance Configuration
| Item | Setting | Description |
|------|---------|-------------|
| **Instance Type** | t2.micro | Free tier compatible (vCPU: 1, Memory: 1GB) |
| **OS** | Amazon Linux 2023 | AWS optimized, Docker compatible |
| **Storage** | 30GB | Sufficient for project within free tier |
| **Security Group** | 8 ports | 22,80,8080,8888, etc. |

#### 🌐 Elastic IP Configuration

**Fixed IP Address Allocation**
Reason for setup: Prevent IP address changes when EC2 instance stops/restarts, ensuring stable service provision and configuration file consistency.

**Database Initial Setup**
```
lease-docker-project/
├── data/
│   ├── mysql/              # Database initial data
│   ├── redis/              # Redis data
│   └── minio/              # MinIO files
└── docker-compose.yml
```
* Data import method: Database files are included during the initial GitHub Push and are designed to be automatically mounted and initialized when Docker Compose starts. For security considerations, the data folder was removed from the repository after deployment completion.

**GitHub Actions (Automated Deployment)**

🔄 Deployment Process
* GitHub Actions Automated Deployment
```yaml
# .github/workflows/deploy.yml
name: Deploy to AWS EC2
on:
  push:
    branches: [ main ]
jobs:
  deploy:
    runs-on: ubuntu-latest
    steps:
      - name: Deploy to EC2
        run: |
          ssh -i ${{ secrets.EC2_PRIVATE_KEY }} ec2-user@${{ secrets.EC2_HOST }}
          cd lease-docker-project
          git pull origin main
          docker-compose down
          docker-compose up -d --build
```
🚨 Issues and Solutions
Issue 1: OOM due to AWS t2.micro Memory Shortage
Symptom: MySQL and other containers repeatedly restart Restarting (137) - OOMKilled
```bash
"OOMKilled": true,
"Status": "restarting", 
Restarting (137) 17 seconds ago
```
Cause:
t2.micro Physical Memory: 1GB
Project Required Memory: ~1.4GB
Result: Memory shortage causing forced container termination
Solution: ✅ Create Swap Virtual Memory + Remove Docker Memory Limitations
```bash
# Create 1GB Swap file
sudo dd if=/dev/zero of=/swapfile bs=1M count=1024
sudo chmod 600 /swapfile
sudo mkswap /swapfile
sudo swapon /swapfile

# Optimize Swap usage strategy
echo 'vm.swappiness=10' | sudo tee -a /etc/sysctl.conf
sudo sysctl vm.swappiness=10

# Remove Docker container memory limitations
# deploy.resources.limits.memory: 80M → Complete removal
```
Effect: Complete resolution of memory issues, doubling total available memory to 2GB
Phenomenon: t2.micro has only 1GB memory, but project requires approximately 1.5GB
```bash
System Available Memory: ~950MB
Project Actual Requirements: 1060-1470MB
Result: Memory shortage, services cannot start normally
```
Solution: ✅ Memory Optimization Configuration
```yaml
# Important optimization parameters
JAVA_OPTS: "-Xmx220m -Xms120m -XX:+UseG1GC"  # Java application memory optimization
innodb-buffer-pool-size=48M                    # MySQL memory optimization
maxmemory 48mb                                  # Redis memory limitation
```
## References
🔗 **Related Repositories**

- [尚硅谷Java项目【尚庭公寓】从0开始Java项目实战](https://www.bilibili.com/video/BV1At421K7gP/)

**Images**
The project images are based on publicly available content from "冠寓", a Chinese apartment platform.

