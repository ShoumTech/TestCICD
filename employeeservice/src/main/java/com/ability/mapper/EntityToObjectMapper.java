package com.ability.mapper;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
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
public interface EntityToObjectMapper {

    EntityToObjectMapper INSTANCE = Mappers.getMapper(EntityToObjectMapper.class);

    @Mappings({ @Mapping(source = "firstName", target = "name.firstName"),
            @Mapping(source = "middleName", target = "name.middleName"),
            @Mapping(source = "lastName", target = "name.lastName"),
            @Mapping(source = "SSN", target = "document.value"),
            @Mapping(source = "confirmSSN", target = "document.confirmValue"),
            @Mapping(target = "phoneNumber", source = "phoneNumber", qualifiedByName = "toPhoneNumber"),
            @Mapping(target = "workPhoneNumber", source = "workPhoneNumber", qualifiedByName = "toPhoneNumber")

    })
    PersonalInfo mapEmployeeEntityToPersonalInfo(EmployeeEntity employee);

    Address mapAddressEntityToAddress(AddressEntity addressEntity);

    @Mappings({ @Mapping(source = "firstName", target = "name.firstName"),
            @Mapping(source = "middleName", target = "name.middleName"),
            @Mapping(source = "lastName", target = "name.lastName"),
            @Mapping(source = "phoneNumber", target = "phoneNumber", qualifiedByName = "toPhoneNumber"),
            @Mapping(source = "altPhoneNumber", target = "altPhoneNumber", qualifiedByName = "toPhoneNumber") })
    EmergencyContact mapToEmergencyContact(EmergencyContactEntity emergencyContactEntity);


    
    @Mappings({ 
        @Mapping(source = "lienseNumber", target = "driverLienseDetail.value"),
        @Mapping(source = "lienseExpirationDate", target = "driverLienseDetail.expiration", dateFormat = "yyyy-MM-dd"),
        @Mapping(target = "driverLienseDetail.type", constant = "DRIVER_LICENSE" )

    })
    TransportInfo mapToEmployeeTransport(TransportEntity empTransport);
    
    Availability mapToEmployeeAvailability(EmployeeAvailabilityEntity empAvailabilityEntity);

    
    @Named("toPhoneNumber")
    default Phone translateToPhone(String phoneNumber) {
        Phone phone = new Phone();
        if (StringUtils.isNoneEmpty(phoneNumber)) {
            String[] strArray = phoneNumber.split("-");
            if (strArray != null && strArray.length == 2) {
                phone.setCountryCode(strArray[0]);
                phone.setMobileNumber(strArray[1]);
            }
        }
        return phone;
    }

    
    Education mapToEmployeeEducation(EducationEntity eduEntity);
    
    @Mappings({ 
        @Mapping(source = "phoneNumber", target = "phoneNumber", qualifiedByName = "toPhoneNumber"),
    })
    Experience mapToEmployeeExperience(ExperienceEntity expEntity);

    @Mappings({ 
        @Mapping(source = "phoneNumber", target = "phoneNumber", qualifiedByName = "toPhoneNumber"),
        @Mapping(source = "firstName", target = "name.firstName"),
        @Mapping(source = "middleName", target = "name.middleName"),
        @Mapping(source = "lastName", target = "name.lastName")
    })
    Reference mapToEmployeeReferencce(ReferenceEntity expEntity);
}
