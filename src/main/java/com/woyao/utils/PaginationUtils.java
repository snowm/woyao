package com.woyao.utils;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.snowm.utils.query.PaginationBean;

public abstract class PaginationUtils {

	public static <T> PaginationBean<T> paging(List<T> collection, long pageNumber, int pageSize) {
		PaginationBean<T> pb = new PaginationBean<>();
		pb.setPageNumber(pageNumber);
		pb.setPageSize(pageSize);
		if (CollectionUtils.isEmpty(collection)) {
			pb.setResults(Collections.emptyList());
			pb.setTotalCount(0L);
			return pb;
		}
		
		int fromIndex = ((int) pageNumber - 1) * pageSize;
		if (fromIndex < 0) {
			fromIndex = 0;
		}

		int toIndex = (int) pageNumber * pageSize;
		if (toIndex > collection.size()) {
			toIndex = collection.size();
		}

		List<T> subRecords = collection.subList(fromIndex, toIndex);
		pb.setResults(subRecords);
		pb.setTotalCount(collection.size());
		return pb;
	}
}
