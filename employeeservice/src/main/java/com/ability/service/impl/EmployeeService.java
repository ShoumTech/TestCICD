package com.ability.service.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ability.entity.AddressEntity;
import com.ability.entity.EmergencyContactEntity;
import com.ability.entity.EmployeeAvailabilityEntity;
import com.ability.entity.EmployeeEntity;
import com.ability.entity.EmployeeSkillSetEntity;
import com.ability.entity.OneTimePassCodeEntity;
import com.ability.entity.TransportEntity;
import com.ability.exception.ProcessingException;
import com.ability.mapper.EntityToObjectMapper;
import com.ability.mapper.ObjectToEntityMapper;
import com.ability.model.Address;
import com.ability.model.Availability;
import com.ability.model.Education;
import com.ability.model.EmergencyContact;
import com.ability.model.Employee;
import com.ability.model.Experience;
import com.ability.model.GenerateOneTimePassCodeRequest;
import com.ability.model.GenerateOneTimePassCodeResponse;
import com.ability.model.KeyValuePair;
import com.ability.model.OneTimePassAuthRequest;
import com.ability.model.OneTimePassAuthResponse;
import com.ability.model.OneTimePassAuthResponse.OneTimePassAuthResponseBuilder;
import com.ability.model.PersonalInfo;
import com.ability.model.QueryFieldsEnum;
import com.ability.model.Reference;
import com.ability.model.SearchData;
import com.ability.model.SearchRequest;
import com.ability.model.SearchResponse;
import com.ability.model.TaskReviewInfo;
import com.ability.model.TransportInfo;
import com.ability.repository.EmergencyContactRepository;
import com.ability.repository.EmpAvailabilityRepository;
import com.ability.repository.EmpTransportRepository;
import com.ability.repository.EmployeeRepository;
import com.ability.repository.EmployeeSkillsetRepository;
import com.ability.repository.OneTimePasscodeRepository;
import com.ability.util.DateUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeeService {

    @Autowired
    private EmergencyContactRepository emergencyContactRepository;

    @Autowired
    private EmpAvailabilityRepository empAvailabilityRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmpTransportRepository empTransportRepository;

    @Autowired
    private EmployeeSkillsetRepository skillSetRepository;

    @Autowired
    private EmployeeExperienceService experienceService;
    
    @Autowired
    private EmployeeEducationService educationService;
    
    @Autowired
    private EmployeeReferenceService referenceService;
    
    @Autowired
    private EmployeeProfileService profileService;
    
    @Autowired
    private EmployeeSpecification employeeSpecification;
    
    @Autowired
    private OneTimePasscodeRepository otpRepository;
    
    @Value("${pagination.page.size.default}")
    private Integer defaultPageSize;

    @Value("${messageingserv.baseUrl}")
    private String messagingservBaseUrl;

    @Autowired
    private RestTemplate restTemplate;

    
    

   
    private AddressEntity populateAddressEntity(Address address, long currentTime) {
        if (Objects.isNull(address)) {
            return null;
        }
        AddressEntity addressEntity = ObjectToEntityMapper.INSTANCE.mapAddressToAddressEntity(address);
        if(addressEntity != null && addressEntity.getAddressId() == null) {
            addressEntity.setTimeCreated(currentTime);
        }
        addressEntity.setTimeUpdated(currentTime);
        return addressEntity;
    }

    private EmergencyContactEntity populateEmergencyContactEntity(EmergencyContact emergencyContact, long currentTime) {
        if (Objects.isNull(emergencyContact)) {
            return null;
        }
        EmergencyContactEntity emergencyContactEntity = ObjectToEntityMapper.INSTANCE
                .mapToEmergencyContactEntity(emergencyContact);
        if(emergencyContactEntity != null && emergencyContactEntity.getEmergencyContactId() == null) {
            emergencyContactEntity.setTimeCreated(currentTime);
        }
        emergencyContactEntity.setTimeUpdated(currentTime);
        emergencyContactEntity.setAddressEntity(populateAddressEntity(emergencyContact.getAddress(), currentTime));
        return emergencyContactEntity;
    }

    private TransportEntity populateTransportEntity(TransportInfo transportInfo, Long currentTime) {
        if (Objects.isNull(transportInfo)) {
            return null;
        }
        TransportEntity transportEntity = ObjectToEntityMapper.INSTANCE.mapToTransportEntity(transportInfo);
        if(transportEntity != null && transportEntity.getTransportId() == null) {
            transportEntity.setTimeCreated(currentTime);
        }
        transportEntity.setTimeUpdated(currentTime);
        Date expDate = DateUtils.asDate(transportInfo.getDriverLienseDetail().getExpiration());
        transportEntity.setLienseExpirationDate(expDate);
        return transportEntity;
    }

    private EmployeeAvailabilityEntity populateAvailabilityEntity(Availability availability, Long currentTime) {
        if (Objects.isNull(availability)) {
            return null;
        }
        EmployeeAvailabilityEntity empAvailabilityEntity = ObjectToEntityMapper.INSTANCE
                .mapToEmpAvailabilityEntity(availability);
        if(empAvailabilityEntity != null && empAvailabilityEntity.getAvailabilityId() == null) {
            empAvailabilityEntity.setTimeCreated(currentTime);
        }
        empAvailabilityEntity.setTimeUpdated(currentTime);
        Date startDate = DateUtils.asDate(availability.getAvailableStartDate());
        empAvailabilityEntity.setAvailableStartDate(startDate);
        return empAvailabilityEntity;
    }


   

    public EmergencyContact createEmergencyContact(Long employeeId, EmergencyContact emergencyContact)
            throws ProcessingException {
        long currentTime = Instant.now().getEpochSecond();
        try {
            EmergencyContactEntity emergencyContactEntity = populateEmergencyContactEntity(emergencyContact,
                    currentTime);
            if (Objects.nonNull(emergencyContactEntity)) {
                EmployeeEntity employeeEntity = profileService.getEmployeeEntity(employeeId);
                emergencyContactEntity.setEmployeeEntity(employeeEntity);
                profileService.updateActiveIndex(QueryFieldsEnum.EMERGENCY_CONTACT.name(), employeeEntity);
                emergencyContactRepository.save(emergencyContactEntity);
                emergencyContact.setEmergencyContactId(emergencyContactEntity.getEmergencyContactId().intValue());
            }
        } catch (Exception e) {
            throw new ProcessingException(e);
        }
        return emergencyContact;
    }

    public TransportInfo createTransportInfo(Long employeeId, TransportInfo transportInfo) throws ProcessingException {
        long currentTime = Instant.now().getEpochSecond();
        try {
            TransportEntity transEntity = populateTransportEntity(transportInfo, currentTime);
            if (Objects.nonNull(transEntity)) {
                EmployeeEntity employeeEntity = profileService.getEmployeeEntity(employeeId);
                transEntity.setEmployeeEntity(employeeEntity);
                empTransportRepository.save(transEntity);
                profileService.updateActiveIndex(QueryFieldsEnum.TRANSPORT.name(), employeeEntity);
                transportInfo.setTransportId(transEntity.getTransportId().intValue());
            }
        } catch (Exception e) {
            throw new ProcessingException(e);
        }
        return transportInfo;
    }

    public Availability createEmployeeAvailability(long employeeId, Availability availability)
            throws ProcessingException {
        long currentTime = Instant.now().getEpochSecond();
        try {
            EmployeeAvailabilityEntity empAvailEntity = populateAvailabilityEntity(availability, currentTime);
            if (Objects.nonNull(empAvailEntity)) {
                EmployeeEntity employeeEntity = profileService.getEmployeeEntity(employeeId);
                empAvailEntity.setEmployeeEntity(employeeEntity);
                empAvailabilityRepository.save(empAvailEntity);
                profileService.updateActiveIndex(QueryFieldsEnum.AVAILABILITY.name(), employeeEntity);

                availability.setAvailabilityId(empAvailEntity.getAvailabilityId().intValue());
            }
        } catch (Exception e) {
            throw new ProcessingException(e);
        }
        return availability;
    }

  
    public TaskReviewInfo createEmployeeSkillSet(long employeeId, TaskReviewInfo taskReviewInfo)
            throws ProcessingException {
        List<EmployeeSkillSetEntity> skillSetList = new ArrayList<>();
        try {
            if (Objects.isNull(taskReviewInfo)) {
                throw new ProcessingException("INVALID_REQUEST", "MISSING_REQUIRED_INFO");
            }
            EmployeeEntity employeeEntity = profileService.getEmployeeEntity(employeeId);
            
            List<EmployeeSkillSetEntity> skillSetEntityList = skillSetRepository.findByEmployeeId(employeeId);
            if(CollectionUtils.isNotEmpty(skillSetEntityList)){
                updateEmployeeSkillSet(taskReviewInfo, skillSetEntityList);
            }
            else {
                insertEmployeeSkillSet(taskReviewInfo, skillSetList, employeeEntity);
            }
           
            profileService.updateActiveIndex(QueryFieldsEnum.SKILLSET.name(), employeeEntity);

        } catch (Exception e) {
            throw new ProcessingException(e);
        }
        return taskReviewInfo;
    }

    private void insertEmployeeSkillSet(TaskReviewInfo taskReviewInfo, List<EmployeeSkillSetEntity> skillSetList,
            EmployeeEntity employeeEntity) {
        if (CollectionUtils.isNotEmpty(taskReviewInfo.getActivityLiving())) {
            taskReviewInfo.getActivityLiving().forEach(keyValuePair -> {
                EmployeeSkillSetEntity employeeSkillSetEntity = new EmployeeSkillSetEntity();
                employeeSkillSetEntity.setSkillsetGroup("ACTIVITY");
                employeeSkillSetEntity.setSkillsetName(keyValuePair.getKey());
                employeeSkillSetEntity.setSkillsetValue(keyValuePair.getValue());
                employeeSkillSetEntity.setEmployeeEntity(employeeEntity);
                skillSetList.add(employeeSkillSetEntity);
            });
        }

        if (CollectionUtils.isNotEmpty(taskReviewInfo.getAssistingWith())) {
            taskReviewInfo.getAssistingWith().forEach(keyValuePair -> {
                EmployeeSkillSetEntity employeeSkillSetEntity = new EmployeeSkillSetEntity();
                employeeSkillSetEntity.setSkillsetGroup("ASSIST");
                employeeSkillSetEntity.setSkillsetName(keyValuePair.getKey());
                employeeSkillSetEntity.setSkillsetValue(keyValuePair.getValue());
                employeeSkillSetEntity.setEmployeeEntity(employeeEntity);
                skillSetList.add(employeeSkillSetEntity);
            });
        }

        if (CollectionUtils.isNotEmpty(taskReviewInfo.getHabitation())) {
            taskReviewInfo.getHabitation().forEach(keyValuePair -> {
                EmployeeSkillSetEntity employeeSkillSetEntity = new EmployeeSkillSetEntity();
                employeeSkillSetEntity.setSkillsetGroup("HABITATION");
                employeeSkillSetEntity.setSkillsetName(keyValuePair.getKey());
                employeeSkillSetEntity.setSkillsetValue(keyValuePair.getValue());
                employeeSkillSetEntity.setEmployeeEntity(employeeEntity);
                skillSetList.add(employeeSkillSetEntity);
            });
        }
        if(CollectionUtils.isNotEmpty(taskReviewInfo.getNightShiftDetails())) {
            taskReviewInfo.getNightShiftDetails().forEach( item ->{
                EmployeeSkillSetEntity employeeSkillSetEntity = new EmployeeSkillSetEntity();
                employeeSkillSetEntity.setSkillsetGroup("SHIFT_DETAILS");
                employeeSkillSetEntity.setSkillsetName(item.getKey());
                employeeSkillSetEntity.setSkillsetValue(item.getValue());
                employeeSkillSetEntity.setEmployeeEntity(employeeEntity);
                skillSetList.add(employeeSkillSetEntity);
            });
        }

        if (CollectionUtils.isNotEmpty(skillSetList)) {
            skillSetRepository.saveAll(skillSetList);
        }
    }

    private void updateEmployeeSkillSet(TaskReviewInfo taskReviewInfo,
            List<EmployeeSkillSetEntity> skillSetEntityList) {
        skillSetEntityList.stream().forEach( skillSetEntity -> {
            if (CollectionUtils.isNotEmpty(taskReviewInfo.getActivityLiving())) {
                Optional<KeyValuePair> keyValPair = taskReviewInfo.getActivityLiving().stream().filter(filter -> filter.getKey().equals(skillSetEntity.getSkillsetName())).findFirst();
                if(keyValPair.isPresent()) {
                    skillSetEntity.setSkillsetValue(keyValPair.get().getValue());
                }
            }
            
            if (CollectionUtils.isNotEmpty(taskReviewInfo.getAssistingWith())) {
                Optional<KeyValuePair> keyValPair = taskReviewInfo.getAssistingWith().stream().filter(filter -> filter.getKey().equals(skillSetEntity.getSkillsetName())).findFirst();
                if(keyValPair.isPresent()) {
                    skillSetEntity.setSkillsetValue(keyValPair.get().getValue());
                }
            }
            if (CollectionUtils.isNotEmpty(taskReviewInfo.getHabitation())) {
                Optional<KeyValuePair> keyValPair = taskReviewInfo.getHabitation().stream().filter(filter -> filter.getKey().equals(skillSetEntity.getSkillsetName())).findFirst();
                if(keyValPair.isPresent()) {
                    skillSetEntity.setSkillsetValue(keyValPair.get().getValue());
                }
            }
            if(CollectionUtils.isNotEmpty(taskReviewInfo.getNightShiftDetails())) {
                Optional<KeyValuePair> keyValPair = taskReviewInfo.getNightShiftDetails().stream().filter(filter -> filter.getKey().equals(skillSetEntity.getSkillsetName())).findFirst();
                if(keyValPair.isPresent()) {
                    skillSetEntity.setSkillsetValue(keyValPair.get().getValue());
                }
            }
            skillSetRepository.saveAll(skillSetEntityList);
        });
    }

 

   
    public Employee getEmployeeDetails(String employeeId, List<String> fields) throws ProcessingException {
        Employee employee = new Employee();
        try {
            EmployeeEntity employeeEntity = null;
            if (StringUtils.isNoneEmpty(employeeId) && StringUtils.isNumeric(employeeId)) {
                employeeEntity = employeeRepository.findByEmployeeId(Long.parseLong(employeeId));
            } else {
                throw new ProcessingException("INVALID_REQUEST", "EMP_ID_IS_EMPTY_OR_INVALID");
            }
            List<QueryFieldsEnum> enumList = fields.stream().map(QueryFieldsEnum::valueOf).collect(Collectors.toList());
            for (QueryFieldsEnum queryField : enumList) {
                switch (queryField) {
                case AVAILABILITY:
                       Availability availability = getEmployeeAvailability(employeeId);
                       employee.setAvailability(availability);
                       break;
                case EDUCATION:
                        List<Education> educationList = educationService.getEmployeeEducationList(employeeId);
                        employee.setEducation(educationList);
                        break;
                case EMERGENCY_CONTACT:
                        EmergencyContact emergencyContact = getEmergencyContact(employeeId);
                        employee.setEmergencyContact(emergencyContact);
                        break;
                case EXPERIENCE:
                        List<Experience> empList = experienceService.getEmployeeExperiences(employeeId);
                        employee.setExperience(empList);
                        break;
                case PROFILE:
                       PersonalInfo personalInfo = profileService.getPersonalInfo(employeeId, employee);
                       employee.setPersonalInfo(personalInfo);
                       break;
                case SKILLSET:
                        TaskReviewInfo skillSet = getEmployeeSkillSet(employeeId);
                        employee.setTaskReviewInfo(skillSet);
                        break;
                case TRANSPORT:
                        TransportInfo transport = getEmployeeTransportInfo(employeeId);
                        employee.setTransportInfo(transport);
                        break;
                case REFERENCE:
                        List<Reference> referenceList = referenceService.getEmployeeReferences(employeeId);
                        employee.setReference(referenceList);
                        break;
                case ALL:
                    availability = getEmployeeAvailability(employeeId);
                    employee.setAvailability(availability);
                    educationList = educationService.getEmployeeEducationList(employeeId);
                    employee.setEducation(educationList);
                    emergencyContact = getEmergencyContact(employeeId);
                    employee.setEmergencyContact(emergencyContact);
                    empList = experienceService.getEmployeeExperiences(employeeId);
                    employee.setExperience(empList);
                    personalInfo = profileService.getPersonalInfo(employeeId, employee);
                    employee.setPersonalInfo(personalInfo);
                    skillSet = getEmployeeSkillSet(employeeId);
                    employee.setTaskReviewInfo(skillSet);
                    transport = getEmployeeTransportInfo(employeeId);
                    employee.setTransportInfo(transport);
                    referenceList = referenceService.getEmployeeReferences(employeeId);
                    employee.setReference(referenceList);
                    break;

                default:
                    break;
                }
            }
            PersonalInfo personalInfo = EntityToObjectMapper.INSTANCE.mapEmployeeEntityToPersonalInfo(employeeEntity);

            Address address = EntityToObjectMapper.INSTANCE
                    .mapAddressEntityToAddress(employeeEntity.getAddressEntity());
            employee.setPersonalInfo(personalInfo);
            personalInfo.setAddress(address);
            employee.setEmployeeId(personalInfo.getEmployeeId());
            employee.setEmailAddress(personalInfo.getEmailAddress());

        } catch (Exception e) {
            throw new ProcessingException(e);
        }
        return employee;
    }
    
    public EmergencyContact getEmergencyContact(String employeeId) throws ProcessingException {
        EmergencyContact emergencyCotact = new EmergencyContact();
        try {
            if (StringUtils.isNoneEmpty(employeeId) && StringUtils.isNumeric(employeeId)) {
                List<EmergencyContactEntity> emergencyContactList = emergencyContactRepository
                        .findByEmployeeId(Long.parseLong(employeeId));

                if (CollectionUtils.isNotEmpty(emergencyContactList)) {
                    EmergencyContactEntity emerContact = emergencyContactList.get(0);
                    emergencyCotact = EntityToObjectMapper.INSTANCE.mapToEmergencyContact(emerContact);
                    if (Objects.nonNull(emerContact.getAddressEntity())) {
                        Address address = EntityToObjectMapper.INSTANCE
                                .mapAddressEntityToAddress(emerContact.getAddressEntity());
                        emergencyCotact.setAddress(address);
                    }
                }
            }
        } catch (Exception e) {
            throw new ProcessingException(e);
        }
        return emergencyCotact;
    }

    public TransportInfo getEmployeeTransportInfo(String employeeId) throws ProcessingException {
        TransportInfo employeeTransport = new TransportInfo();
        try {
            if (StringUtils.isNoneEmpty(employeeId) && StringUtils.isNumeric(employeeId)) {
                List<TransportEntity> transportEntityList = empTransportRepository
                        .findByEmployeeId(Long.parseLong(employeeId));

                if (CollectionUtils.isNotEmpty(transportEntityList)) {
                    TransportEntity empTransport = transportEntityList.get(0);
                    employeeTransport = EntityToObjectMapper.INSTANCE.mapToEmployeeTransport(empTransport);
                }
            }
        } catch (Exception e) {
            throw new ProcessingException(e);
        }
        return employeeTransport;
    }

    public Availability getEmployeeAvailability(String employeeId) throws ProcessingException {
        Availability availability = new Availability();
        try {
            if (StringUtils.isNoneEmpty(employeeId) && StringUtils.isNumeric(employeeId)) {
                List<EmployeeAvailabilityEntity> empAvailEntityList = empAvailabilityRepository
                        .findByEmployeeId(Long.parseLong(employeeId));
                if (CollectionUtils.isNotEmpty(empAvailEntityList)) {
                    EmployeeAvailabilityEntity empAvailabilityEntity = empAvailEntityList.get(0);
                    availability = EntityToObjectMapper.INSTANCE.mapToEmployeeAvailability(empAvailabilityEntity);
                }
            }
        } catch (Exception e) {
            throw new ProcessingException(e);
        }
        return availability;
    }

    

    public Employee createMinEmployee(String emailAddress) throws ProcessingException {
        Employee emp = new Employee();
        try {
            long currentTime = Instant.now().getEpochSecond();
            if (Objects.isNull(emailAddress)) {
                return null;
            }
            EmployeeEntity employeeEntity = employeeRepository.findByEmailAddress(emailAddress);
            if (Objects.isNull(employeeEntity)) {
                employeeEntity = new EmployeeEntity();
                employeeEntity.setTimeCreated(currentTime);
                employeeEntity.setIsConsentSigned("NO");
                employeeEntity.setIsVisited("NO");
                employeeEntity.setTimeUpdated(currentTime);
                employeeEntity.setEmailAddress(emailAddress);
                employeeRepository.save(employeeEntity);
                emp.setEmployeeId(employeeEntity.getEmployeeId().intValue());
            } else {
                emp.setEmployeeId(employeeEntity.getEmployeeId().intValue());
            }
        } catch (Exception e) {
            throw new ProcessingException(e);
        }
        return emp;
    }


    public TaskReviewInfo getEmployeeSkillSet(String employeeId) throws ProcessingException {
        TaskReviewInfo taskReviewInfo = new TaskReviewInfo();
        try {
            if (StringUtils.isNoneEmpty(employeeId) && StringUtils.isNumeric(employeeId)) {
               List<EmployeeSkillSetEntity> skillSetEntityList = skillSetRepository.findByEmployeeId(Long.parseLong(employeeId));
               if(CollectionUtils.isNotEmpty(skillSetEntityList)) {
                   List<KeyValuePair> habitationList = new ArrayList<>();
                   List<KeyValuePair> activityList = new ArrayList<>();
                   List<KeyValuePair> assistingList = new ArrayList<>();
                   List<KeyValuePair> shiftDetails = new ArrayList<>();

                   for(EmployeeSkillSetEntity skillSetEntity : skillSetEntityList) {
                       KeyValuePair keyPair = createKeyPair(skillSetEntity);
                       switch (skillSetEntity.getSkillsetGroup()) {
                            case "ACTIVITY":
                                activityList.add(keyPair);
                                break;
                            case "ASSIST":
                                assistingList.add(keyPair);
                                break;
                            case "HABITATION":
                                habitationList.add(keyPair);
                                break;
                            case "SHIFT_DETAILS":
                                shiftDetails.add(keyPair);
                                break;
                            default:
                                break;
                           }
                   }
                   taskReviewInfo.setActivityLiving(activityList);
                   taskReviewInfo.setAssistingWith(assistingList);
                   taskReviewInfo.setHabitation(habitationList );
                   taskReviewInfo.setNightShiftDetails(shiftDetails);
               }
            }
        } catch (Exception e) {
            throw new ProcessingException(e);
        }
       
        return taskReviewInfo;
    }
    private KeyValuePair createKeyPair(EmployeeSkillSetEntity skillSet) {
        KeyValuePair keyPair = new KeyValuePair();
        keyPair.setKey(skillSet.getSkillsetName());
        keyPair.setValue(skillSet.getSkillsetValue());
        return keyPair;
    }

    public boolean markVisited(String employeeId, Employee employee) throws ProcessingException {
        try {
           EmployeeEntity employeeEntity = profileService.getEmployeeEntity(Long.parseLong(employeeId));
          if(employee.getIsVisited() != null) {
              employeeEntity.setIsVisited(employee.getIsVisited().getValue());
              employeeRepository.save(employeeEntity);
              return true;
          }
        }catch (Exception e) {
            throw new ProcessingException(e);
        }
        return false;
    }
    
    public boolean updateDeclaration(String employeeId, Employee employee) throws ProcessingException {
        long currentTime = Instant.now().getEpochSecond();
        try {
           EmployeeEntity employeeEntity = profileService.getEmployeeEntity(Long.parseLong(employeeId));
          if(employee.getSignature() != null) {
              employeeEntity.setSignature(employee.getSignature());
              employeeEntity.setTimeUpdated(currentTime);
              employeeRepository.save(employeeEntity);
              profileService.updateActiveIndex(QueryFieldsEnum.CONSENT.name(), employeeEntity);
              return true;
          }
        }catch (Exception e) {
            throw new ProcessingException(e);
        }
        return false;
    }
    
    public boolean updateConsent(String employeeId, Employee employee) throws ProcessingException {
        long currentTime = Instant.now().getEpochSecond();
        try {
           EmployeeEntity employeeEntity = profileService.getEmployeeEntity(Long.parseLong(employeeId));
          if(employee.getIsConsentSigned() != null) {
              employeeEntity.setIsConsentSigned(employee.getIsConsentSigned().getValue());
              employeeEntity.setTimeUpdated(currentTime);
              employeeRepository.save(employeeEntity);
              profileService.updateActiveIndex(QueryFieldsEnum.CONSENT.name(), employeeEntity);
              return true;
          }
        }catch (Exception e) {
            throw new ProcessingException(e);
        }
        return false;
    }
  
    public SearchResponse getEmployeeList(SearchRequest request) throws ProcessingException {
        
        List<EmployeeEntity> list = null;
        Page<EmployeeEntity> pages = null;
        try {
        if (request.getPageNumber() == null) {
            pages = new PageImpl<>(employeeRepository.findAll(employeeSpecification.getEmployees(request)));
        } else {
            if (request.getPageSize() == null) {
                request.setPageSize(defaultPageSize);
            }
            Pageable paging = PageRequest.of(request.getPageNumber() - 1, request.getPageSize());
            pages = employeeRepository.findAll(employeeSpecification.getEmployees(request), paging);
        }
        if (pages != null && pages.getContent() != null) {
            list = pages.getContent();
            if (list != null && list.size() > 0) {
                SearchResponse searchResponse = new SearchResponse();

                searchResponse.setTotalPages(pages.getTotalPages());
                searchResponse.setTotalCount(pages.getTotalElements());
                searchResponse.setPageNo(pages.getNumber() + 1);
                searchResponse.setDataList(new ArrayList<>());
                int unreadCounter = 0;
                for (EmployeeEntity empEntity : list) {
                    SearchData searchData = new SearchData();
                    searchData.setEmailAddress(empEntity.getEmailAddress());
                    searchData.setEmployeeId(empEntity.getEmployeeId());
                    searchData.setFirstName(empEntity.getFirstName());
                    searchData.setLastName(empEntity.getLastName());
                    searchData.setMiddleName(empEntity.getMiddleName());
                    searchData.setGender(empEntity.getGender());
                    searchData.setCreatedDate(DateUtils.asLocalDate(empEntity.getTimeCreated()));
                    searchData.setUpdatedDate(DateUtils.asLocalDate(empEntity.getTimeCreated()));
                    searchData.setIsVisited(empEntity.getIsVisited());
                    searchData.setIsConsentSigned(empEntity.getIsConsentSigned());
                    if(!"Y".equalsIgnoreCase(empEntity.getIsVisited())) {
                        unreadCounter++;
                    }
                    if(empEntity.getPhoneNumber() != null) {
                        searchData.setPhoneNumber(EntityToObjectMapper.INSTANCE.translateToPhone(empEntity.getPhoneNumber()));
                    }
                    searchResponse.setTotalNumberOfUnReadEmployees(String.valueOf(unreadCounter));
                    searchResponse.getDataList().add(searchData);
                }
                return searchResponse;
            }
        }
        }catch (Exception e) {
            throw new ProcessingException(e);
        }
        return null;
    }

    public Employee getDeclaration(String employeeId) throws ProcessingException {
       Employee emp = null;
        try {
            EmployeeEntity employeeEntity = profileService.getEmployeeEntity(Long.parseLong(employeeId));
            if(employeeEntity != null) {
                emp = new Employee();
                emp.setSignature(employeeEntity.getSignature());
            }
        }catch (Exception e) {
            throw new ProcessingException(e);
        }
        return emp;
    }

    public OneTimePassAuthResponse authenticateOTP(OneTimePassAuthRequest request) throws ProcessingException {
        OneTimePassAuthResponseBuilder builder = OneTimePassAuthResponse.builder();
        try {
            EmployeeEntity employeeEntity = employeeRepository.findByEmailAddress(request.getEmailAddress());

            Long employeeId = employeeEntity.getEmployeeId();
            
             OneTimePassCodeEntity oneTimePassEntity = otpRepository.findbyEmployeeId(employeeId);
            if(oneTimePassEntity != null) {
                if(oneTimePassEntity.getOtpCode().equals(request.getOneTimePasscode()) &&
                        !"Y".equalsIgnoreCase(oneTimePassEntity.getIsExpired()) && isOTPTimeBetweenValid(oneTimePassEntity)) {
                    oneTimePassEntity.setIsExpired("Y"); //Update One Time Password JPA.
                    otpRepository.save(oneTimePassEntity); // Persist Update
                    return builder.emailAddress(employeeEntity.getEmailAddress())
                            .isOneTimePasscodeValid(true).build();
                }
            }
            return builder.emailAddress(employeeEntity.getEmailAddress())
            .isOneTimePasscodeValid(false).build();
        }catch (Exception e) {
            throw new ProcessingException();
        }
    }

    private boolean isOTPTimeBetweenValid(OneTimePassCodeEntity oneTimePassEntity) {
        long currentTime = Instant.now().getEpochSecond();
        return oneTimePassEntity != null &&
                currentTime <= oneTimePassEntity.getExpiraryTime() &&
                currentTime > oneTimePassEntity.getGeneratedTime();               
    }

    public GenerateOneTimePassCodeResponse generateOTP(GenerateOneTimePassCodeRequest request) throws ProcessingException {
        long otpValitity = 5*60;
        long currentTime = Instant.now().getEpochSecond();
        GenerateOneTimePassCodeResponse response = new GenerateOneTimePassCodeResponse();
        try {
            EmployeeEntity employeeEntity = employeeRepository.findByEmailAddress(request.getEmailAddress());
            if(employeeEntity == null) {
                response.setStatus("INVALID_EMPLOYEE_ID");
                return response;
            }
            String passCode = getRandomNumberString();
            OneTimePassCodeEntity otpEntity = otpRepository.findbyEmployeeId(employeeEntity.getEmployeeId());
            if(otpEntity != null) {
                otpEntity.setExpiraryTime(currentTime+otpValitity);
                otpEntity.setGeneratedTime(currentTime);
                otpEntity.setIsExpired("N");
                otpEntity.setOtpCode(passCode);
                otpRepository.saveAndFlush(otpEntity);
            }else {
                otpEntity = new OneTimePassCodeEntity();
                otpEntity.setEmployeeId(employeeEntity.getEmployeeId());
                otpEntity.setExpiraryTime(currentTime+otpValitity);
                otpEntity.setGeneratedTime(currentTime);
                otpEntity.setIsExpired("N");
                otpEntity.setOtpCode(passCode);
                otpRepository.save(otpEntity);
            }
            sendEmail(employeeEntity.getEmailAddress(), passCode);
            response.setPassCode(passCode);
            response.setEmailAddress(employeeEntity.getEmailAddress());
            response.setEmployeeId(String.valueOf(employeeEntity.getEmployeeId()));
        }catch (Exception e) {
            throw new ProcessingException();
        }
        
        return response;
    }
    
    private void sendEmail(String emailAddress,String tokenString) {
        String url = "";
        try {
            url = messagingservBaseUrl + emailAddress + "/sendEmail/" + tokenString;
            ResponseEntity<String> response = restTemplate.getForEntity(url,String.class);
            HttpStatus status = response.getStatusCode();
            if(status.is2xxSuccessful()) {
                log.info("=========> Email Sent ===> " + response.getBody());
            }else {
                log.info("=========> Email Failed ===> " + response.getBody());

            }
        }catch (Exception e) {
            log.error(e.getMessage());
        }
    }
    
    public String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }
}
