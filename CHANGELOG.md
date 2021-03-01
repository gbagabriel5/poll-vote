# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).


## Types of changes

- Added for new features.
- Changed for changes in existing functionality.
- Deprecated for soon-to-be removed features.
- Removed for now removed features.
- Fixed for any bug fixes.
- Security in case of vulnerabilities.

## [1.1.0] 2021-03-01

### Added
- Kafka on getSessionResult
- Docker Compose with postgre and kafka
- Heroku depedencies

### Removed
- getResultTest, because it uses a external api of user

## [1.0.3] 2021-02-27

### Changed
- Changing database connection from mysql to postgresql

### Fixed
- thrown messages
- swagger

## [1.0.3] 2021-02-27

### Added
- getResultVotes with test
- clean Request Object DTO to Session and Vote

### Changed
- Request Object of Session and Vote
- convertToEntity of VoteMapper
- Return of VoteCreate Service and Controller to Boolean
- startDate type formatter to SessionDTO

### Removed
- convertToDTO of VoteMapper, that was not being used.

## [1.0.2] 2021-02-27

### Added
- Adding vote and your tests
- Exception Handler

### Changed  
- Return of controller endpoints

## [1.0.1] 2021-02-26

### Added
- Session
- Session Service Test
- Generic Mapper
- findAllPoll

### Changed
- Poll mapper to convert entityList for dto and use on controller return of findAll service

### Removed
- MessagesService

## [1.0.0] 2021-02-25

### Added
- Initial Project
- Associate and poll, 
  with their classes of domain, dto, mapper, repository, service, controller 
- Service tests of Associate and Poll
- Project config with swagger, connection to mysql, user cpf verification with feign, mockito, lombok,jpa