package com.woyao.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.woyao.admin.dto.profile.ProfileDTO;
import com.woyao.admin.service.IUserAdminService;
import com.snowm.security.profile.domain.Profile;
import com.snowm.security.profile.domain.ProfileRole;
import com.snowm.security.profile.domain.Role;
import com.snowm.security.profile.service.ProfileRoleService;
import com.snowm.utils.query.PaginationBean;

@Service("userAdminService")
public class UserAdminServiceImpl extends AbstractAdminService<Profile, ProfileDTO> implements IUserAdminService {

	@Resource(name = "woyaoPasswordEncoder")
	private PasswordEncoder passwordEncoder;

	@Resource(name = "defaultProfileRoleService")
	private ProfileRoleService profileRoleService;

	@Transactional
	@Override
	public ProfileDTO create(ProfileDTO dto) {
		Profile m = this.transferToDomain(dto);
		if (!StringUtils.isBlank(dto.getPassword())) {
			String encodedPassword = this.passwordEncoder.encode(dto.getPassword());
			m.setPassword(encodedPassword);
		}
		this.dao.save(m);
		ProfileDTO rs = this.transferToDTO(m, true);
		List<ProfileRole> profileRoles = this.generateDefaultProfileRoles(m);
		for (ProfileRole profileRole : profileRoles) {
			this.profileRoleService.save(profileRole);
		}
		return rs;
	}

	private List<ProfileRole> generateDefaultProfileRoles(Profile profile) {
		ProfileRole profileRole = new ProfileRole();
		profileRole.setProfile(profile);
		Role role = new Role();
		role.setId(-103L);
		profileRole.setRole(role);
		List<ProfileRole> profileRoles = new ArrayList<>();
		profileRoles.add(profileRole);
		return profileRoles;
	}

	@Transactional
	@Override
	public ProfileDTO update(ProfileDTO dto) {
		Profile m = this.dao.get(Profile.class, dto.getId());
		if (!StringUtils.isBlank(dto.getPassword())) {
			String encodedPassword = this.passwordEncoder.encode(dto.getPassword());
			m.setPassword(encodedPassword);
		}
		BeanUtils.copyProperties(dto, m, "username","password");
		this.dao.update(m);
		ProfileDTO rs = this.transferToDTO(m, true);
		return rs;
	}

	@Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
	@Override
	public PaginationBean<ProfileDTO> query(String name, Boolean enabled, long pageNumber, int pageSize) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		if (!StringUtils.isEmpty(name)) {
			criterions.add(Restrictions.like("username", "%" + name + "%"));
		}

		if (enabled != null) {
			criterions.add(Restrictions.eq("enabled", enabled));
		}
		long count = this.dao.count(Profile.class, criterions);
		List<Profile> profiles = new ArrayList<>();
		if (count > 0l) {
			profiles = this.dao.query(Profile.class, criterions, pageNumber, pageSize);
		}

		PaginationBean<ProfileDTO> rs = new PaginationBean<>(pageNumber, pageSize);
		rs.setTotalCount(count);
		List<ProfileDTO> results = new ArrayList<>();
		for (Profile profile : profiles) {
			ProfileDTO dto = this.transferToDTO(profile, true);
			results.add(dto);
		}
		rs.setResults(results);
		return rs;
	}

	@Override
	public Profile transferToDomain(ProfileDTO dto) {
		Profile m = new Profile();
		BeanUtils.copyProperties(dto, m, "password");
		return m;
	}

	@Override
	public ProfileDTO transferToSimpleDTO(Profile m) {
		ProfileDTO dto = new ProfileDTO();
		BeanUtils.copyProperties(m, dto, "modification", "profileRoles", "password");
		return dto;
	}

	@Override
	public ProfileDTO transferToFullDTO(Profile m) {
		return transferToSimpleDTO(m);
	}


}
