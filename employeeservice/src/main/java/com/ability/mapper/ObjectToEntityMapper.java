package com.ability.mapper;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

import com.ability.entity.AddressEntity;
import com.ability.entity.EducationEntity;
import com.ability.entity.EmergencyContactEntity;
import com.ability.entity.EmployeeAvailabilityEntity;
import com.ability.entity.EmployeeEntity;
import com.ability.entity.ExperienceEntity;
import com.ability.entity.ReferenceEntity;
import com.ability.entity.TransportEntity;
import com.ability.model.Address;
import com.ability.model.Availability;
import com.ability.model.Education;
import com.ability.model.EmergencyContact;
import com.ability.model.Experience;
import com.ability.model.PersonalInfo;
import com.ability.model.Phone;
import com.ability.model.Reference;
import com.ability.model.TransportInfo;

@Mapper
public interface ObjectToEntityMapper {

    ObjectToEntityMapper INSTANCE = Mappers.getMapper(ObjectToEntityMapper.class);

    @Mappings({ @Mapping(target = "employeeId", source = "employeeId", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS),
            @Mapping(target = "firstName", source = "name.firstName"),
            @Mapping(target = "middleName", source = "name.middleName"),
            @Mapping(target = "lastName", source = "name.lastName"),
            @Mapping(target = "SSN", source = "document.value"),
            @Mapping(target = "confirmSSN", source = "document.confirmValue"),
            @Mapping(target = "timeCreated", ignore = true), 
            @Mapping(target = "timeUpdated", ignore = true),
            @Mapping(target = "phoneNumber", source="phoneNumber", qualifiedByName = "toPhoneNumber"),
            @Mapping(target = "workPhoneNumber",  source="workPhoneNumber", qualifiedByName = "toPhoneNumber"),

    })
    void mapPersonalInfoToEmployeeEntity(@MappingTarget EmployeeEntity entity, PersonalInfo personalInfo);

    @Mappings({ @Mapping(target = "addressId", source = "addressId",  nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS), 
                @Mapping(target = "timeCreated", ignore = true),
                @Mapping(target = "timeUpdated", ignore = true)})
    AddressEntity mapAddressToAddressEntity(Address address);
    
    @Mappings({ @Mapping(target = "emergencyContactId", source = "emergencyContactId",  nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS),
        @Mapping(target = "firstName", source = "name.firstName"),
        @Mapping(target = "middleName", source = "name.middleName"),
        @Mapping(target = "lastName", source = "name.lastName"),
        @Mapping(target = "phoneNumber",  source="phoneNumber", qualifiedByName = "toPhoneNumber"),
        @Mapping(target = "altPhoneNumber",  source="altPhoneNumber", qualifiedByName = "toPhoneNumber"),
        @Mapping(target = "timeCreated", ignore = true),
        @Mapping(target = "timeUpdated", ignore = true) })
    EmergencyContactEntity mapToEmergencyContactEntity(EmergencyContact emergencyContact);
    
    @Mappings({ @Mapping(target = "transportId", source = "transportId",  nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS), 
        @Mapping(target = "lienseNumber", source = "driverLienseDetail.value"),
        @Mapping(target = "lienseExpirationDate", source = "driverLienseDetail.expiration", dateFormat = "yyyy-MM-dd"),
        @Mapping(target = "timeCreated", ignore = true),
        @Mapping(target = "timeUpdated", ignore = true)})
    TransportEntity mapToTransportEntity(TransportInfo transportInfo);
    
    @Mappings({ @Mapping(target = "availabilityId", source="availabilityId", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS),
        @Mapping(target = "availableStartDate", ignore = true),
        @Mapping(target = "timeCreated", ignore = true),
        @Mapping(target = "timeUpdated", ignore = true)})
    EmployeeAvailabilityEntity mapToEmpAvailabilityEntity(Availability availabilty);
    
    @Mappings({ @Mapping(target = "educationId", source = "educationId", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS), 
        @Mapping(target = "timeCreated", ignore = true),
        @Mapping(target = "timeUpdated", ignore = true)})
    EducationEntity mapToEducationEntity(Education education);
    
    @Mappings({ @Mapping(target = "experienceId", source="experienceId", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS), 
        @Mapping(target = "startDate", ignore = true),
        @Mapping(target = "endDate", ignore = true),
        @Mapping(target = "phoneNumber", source="phoneNumber", qualifiedByName = "toPhoneNumber"),
        @Mapping(target = "timeCreated", ignore = true),
        @Mapping(target = "timeUpdated", ignore = true)})
    ExperienceEntity mapToExperienceEntity(Experience experience);
    
    @Named("toPhoneNumber")
    default String translateToPhone(Phone phone) {
       if(phone != null && phone.getCountryCode() !=null && phone.getMobileNumber() !=null) {
           return phone.getCountryCode() + "-" +phone.getMobileNumber();
       }
        return StringUtils.EMPTY;
    }

    @Mappings({ 
        @Mapping(target = "referenceId", source="referenceId", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS), 
        @Mapping(target = "phoneNumber", source="phoneNumber", qualifiedByName = "toPhoneNumber"),
        @Mapping(target = "firstName", source = "name.firstName"),
        @Mapping(target = "middleName", source = "name.middleName"),
        @Mapping(target = "lastName", source = "name.lastName"),
        @Mapping(target = "timeCreated", ignore = true),
        @Mapping(target = "timeUpdated", ignore = true)})
    ReferenceEntity mapToReferenceEntity(Reference reference);

}
