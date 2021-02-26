package com.nerga.travelCreatorApp.common.propertymap;

import com.nerga.travelCreatorApp.expensesregister.ExpenseRecord;
import com.nerga.travelCreatorApp.expensesregister.Expense;
import com.nerga.travelCreatorApp.expensesregister.dto.ExpensesDetailsDto;
import com.nerga.travelCreatorApp.expensesregister.dto.ExpenseRecordDetailsDto;
import com.nerga.travelCreatorApp.location.Location;
import com.nerga.travelCreatorApp.location.dto.LocationDetailsDto;
import org.modelmapper.PropertyMap;

public class ApplicationPropertyMaps {

    public static PropertyMap<Location, LocationDetailsDto> userEntityFieldMapping() {
        return new PropertyMap<Location, LocationDetailsDto>() {
            @Override
            protected void configure() {
                map().getOwner().setId(source.getOwner().getId());
                map().getOwner().setUsername(source.getOwner().getUsername());
                map().getOwner().setFirstName(source.getOwner().getFirstName());
                map().getOwner().setLastName(source.getOwner().getLastName());
                map().getOwner().setEmail(source.getOwner().getEmail());
                map().getOwner().setPhoneNumber(source.getOwner().getPhoneNumber());
            }
        };
    }

    public static PropertyMap<Expense, ExpensesDetailsDto> expensesRecordFieldMapping(){
        return null;
    }

    public static PropertyMap<ExpenseRecord, ExpenseRecordDetailsDto> expensesRecordUserEntityFieldMapping(){
        return  null;
    }

}
