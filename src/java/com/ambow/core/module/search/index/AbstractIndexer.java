package com.ambow.core.module.search.index;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.paoding.analysis.analyzer.PaodingAnalyzer;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.StaleReaderException;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.LockObtainFailedException;

import com.ambow.core.module.search.bean.DifferentRecord;
import com.ambow.core.module.search.bean.OperationType;
import com.ambow.core.module.search.util.IntParser;

public abstract class AbstractIndexer implements Indexer {

	public void indexAdd(IndexWriter writer, Document doc)
			throws CorruptIndexException, IOException {
		writer.addDocument(doc);
	}

	public void indexDelete(IndexReader reader, Integer id)
			throws StaleReaderException, CorruptIndexException,
			LockObtainFailedException, IOException {
		reader.deleteDocuments(new Term("id", IntParser.intToString(id)));
	}

	public void indexUpdate(IndexWriter writer, Document doc)
			throws CorruptIndexException, IOException {
		writer.updateDocument(new Term("id", doc.get("id")), doc);
	}

	public IndexWriter getIndexWriter(boolean isOverride) throws CorruptIndexException,
			LockObtainFailedException, IOException {
		IndexWriter writer = new IndexWriter(getIndexPath(), new PaodingAnalyzer(), isOverride);

		return writer;
	}

	public IndexReader getIndexReader() throws CorruptIndexException,
			IOException {
		IndexReader reader = IndexReader.open(getIndexPath());

		return reader;
	}

	/**
	 * 关闭IndexWriter
	 * 
	 * @param writer
	 * 
	 * @author Schweigen
	 */
	public void close(IndexWriter writer) {
		if (writer != null) {
			try {
				writer.close();
			} catch (Exception e) {
				// Ignore
			}
		}
	}

	/**
	 * 关闭IndexReader
	 * 
	 * @param reader
	 * 
	 * @author Schweigen
	 */
	public void close(IndexReader reader) {
		try {
			reader.close();
		} catch (Exception e) {
			// Ignore
		}
	}

	public Field createIntField(String name, Integer intval, Store store,
			Index index) {
		if (intval == null) {
			intval = 0;
		}

		return new Field(name, IntParser.intToString(intval), store, index);
	}

	public Field createIntField(String name, BigDecimal decimal, Store store,
			Index index) {
		if (decimal == null) {
			decimal = new BigDecimal(0);
		}

		return createIntField(name, decimal.intValue(), store, index);
	}

	public Field createFloatField(String name, Double intval, Store store,
			Index index) {
		if (intval == null) {
			intval = new Double(0.0); 
		}

		return new Field(name, IntParser.floatToString(intval), store, index);
	}
	
	public Field createStringField(String name, String value, Store store,
			Index index) {
		if (value == null) {
			value = "";
		}

		return new Field(name, value, store, index);
	}
	
	/**
	 * 将一个表的操作进行去重，以尽可能小的代价去更新索引
	 * 
	 * @param recordList 未去重前的List
	 * @return 去重后的List
	 */
	protected List<DifferentRecord> getRidOfRepeat(List<DifferentRecord> recordList) {
		List<DifferentRecord> result = new ArrayList<DifferentRecord>();
		
		Map<Integer, DifferentRecord> idMap = new HashMap<Integer, DifferentRecord>();
		for (DifferentRecord record : recordList) {
			DifferentRecord recordInMap = idMap.get(record.getTargetId());
			if (recordInMap == null) {
				// 如果还没有基于此id 的操作，那么将此操作放入map 中
				idMap.put(record.getTargetId(), record);
			} else {
				
				// 如果是delete 那么其他所有关于此id 的操作将全部忽略
				if (recordInMap.getOperationType() == OperationType.DELETE) {
					continue;
				}
				
				// 如果是insert，那么后面所有的关于此id 的update 操作全部忽略
				if (recordInMap.getOperationType() == OperationType.INSERT) {
					if (record.getOperationType() == OperationType.DELETE) {
						idMap.put(record.getTargetId(), record);
					}
					continue;
				}
				
				// 只有当所有的操作都是update 的时候，才会update，否则为其他操作
				if (recordInMap.getOperationType() == OperationType.UPDATE) {
					if (record.getOperationType() != OperationType.UPDATE) {
						idMap.put(record.getTargetId(), record);
					}
					
					continue;
				}
			}
		}
		
		result.addAll(idMap.values());
		
		return result;
	}

}
