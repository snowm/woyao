package com.snowm.cat.admin.service;

public interface IAdminService<M, DTO> {

	DTO get(long id);

	DTO get(long id, boolean isFull);

	DTO delete(long id);
	
	DTO putback(long id);

	DTO enable(long id);
	
	DTO disable(long id);

	DTO create(DTO dto);

	DTO update(DTO dto);

	M transferToDomain(DTO dto);

	DTO transferToSimpleDTO(M m);

	DTO transferToFullDTO(M m);

	default DTO transferToDTO(M m, boolean isFull) {
		if (isFull) {
			return this.transferToFullDTO(m);
		}
		return this.transferToSimpleDTO(m);
	}

}
