package com.ambow.core.module.search.index;

import java.util.List;

import com.ambow.core.module.search.bean.DifferentRecord;

public interface Indexer {

	public void fullIndex();

	public void addIndex(Integer id);

	public void updateIndex(Integer id);

	public void deleteIndex(Integer id);

	public void incrementIndex(List<DifferentRecord> recordList);

	public String getIndexPath();

}
