package com.suitong.devplatform.web.rest.vm;

import com.suitong.devplatform.service.dto.UserDTO;

import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

/**
 * View Model extending the UserDTO, which is meant to be used in the user management UI.
 */
public class ManagedUserVM extends UserDTO {

    public static final int PASSWORD_MIN_LENGTH = 4;

    public static final int PASSWORD_MAX_LENGTH = 100;

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    public ManagedUserVM() {
        // Empty constructor needed for Jackson.
    }

    public ManagedUserVM(Long id, String login, String firstName, String lastName,
                         String email, String imageUrl, boolean activated, String langKey,
                         String createdBy, Date createdDate, String lastModifiedBy, Date lastModifiedDate,
                         Set<String> authorities, String activationKey, String resetKey, Date resetDate, String charNo,
                         String sex, String idNo, String deptId, String tel, String mobile, Date onboardDate,
                         Date expireDate, String status, String delFlag, Date passwordExpireDate, String department, String sysUsersId, Long errorCount) {

        super(id, login, firstName, lastName,
                email, imageUrl, activated, langKey,
                createdBy, createdDate, lastModifiedBy, lastModifiedDate,
                authorities, activationKey, resetKey, resetDate, charNo,
                sex, idNo, deptId, tel, mobile, onboardDate,
                expireDate, status, delFlag, passwordExpireDate, department, sysUsersId, errorCount);

        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "ManagedUserVM{" +
                "} " + super.toString();
    }
}
