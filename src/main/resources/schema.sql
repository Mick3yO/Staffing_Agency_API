
DROP TABLE IF EXISTS Application;
DROP TABLE IF EXISTS Job;
DROP TABLE IF EXISTS Client;
DROP TABLE IF EXISTS Candidate;

-- Table: Candidate
CREATE TABLE Candidate (
  candidate_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  candidate_name VARCHAR(255) NOT NULL,
  email VARCHAR(255),
  education VARCHAR(255),
  experience VARCHAR(255)
);

-- Table: Client
CREATE TABLE Client (
  client_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  company_name VARCHAR(255) NOT NULL,
  contact_details VARCHAR(255), 
  industry VARCHAR(255)
);

-- Table: Job
CREATE TABLE Job (
  job_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  description VARCHAR(255),
  requirements VARCHAR(255),
  location VARCHAR(255),
  pay VARCHAR(255),
  client_id BIGINT,
  FOREIGN KEY (client_id) REFERENCES Client (client_id) ON DELETE CASCADE
);

-- Table: Application
CREATE TABLE Application (
  application_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  candidate_id BIGINT,
  job_id BIGINT,
  application_date DATE,
  status VARCHAR(255),
  FOREIGN KEY (candidate_id) REFERENCES Candidate (candidate_id) ON DELETE CASCADE,
  FOREIGN KEY (job_id) REFERENCES Job (job_id) ON DELETE CASCADE
);
