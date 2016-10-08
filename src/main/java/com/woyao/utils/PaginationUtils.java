package com.woyao.utils;

import java.util.List;

import com.snowm.utils.query.PaginationBean;

public abstract class PaginationUtils {

	public static <T> PaginationBean<T> paging(List<T> collection, long pageNumber, int pageSize) {
		int fromIndex = ((int) pageNumber - 1) * pageSize;
		if (fromIndex < 0) {
			fromIndex = 0;
		}
		
		int toIndex = (int) pageNumber * pageSize;
		if (toIndex > collection.size()) {
			toIndex = collection.size();
		}

		List<T> subRecords = collection.subList(fromIndex, toIndex);
		PaginationBean<T> pb = new PaginationBean<>();
		pb.setPageNumber(pageNumber);
		pb.setPageSize(pageSize);
		pb.setResults(subRecords);
		pb.setTotalCount(collection.size());
		return pb;
	}
}
