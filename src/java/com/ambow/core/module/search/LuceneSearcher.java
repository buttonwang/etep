package com.ambow.core.module.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.springframework.util.Assert;

import com.ambow.core.dao.support.Page;
import com.ambow.core.module.search.command.BaseSearchCommand;

public class LuceneSearcher {
	private final static Logger log = Logger.getLogger(LuceneSearcher.class);

	public Page search(BaseSearchCommand command, int pageNo, int pageSize) {
		Assert.isTrue(pageNo > 0);
		Assert.isTrue(pageSize > 0);

		try {
			IndexSearcher searcher = new IndexSearcher(command.getIndexPath());
			Hits docs = searcher.search(command.buildQuery(), command
					.getFilter(), command.getSort());

			Page pagedHits = pagedHits(docs, pageNo, pageSize);

			return pagedHits;
		} catch (CorruptIndexException e) {
			log.error("Search Corrupted: " + e.getMessage());
		} catch (IOException e) {
			log.error("IO Exceptions");
			log.error(e.getMessage());
		}

		return null;
	}

	protected Page pagedHits(Hits docs, int pageNo, int pageSize)
			throws CorruptIndexException, IOException {
		List<Document> list = new ArrayList<Document>();
		int count = docs.length();
		int totalPageCount = getTotalPageCount(count, pageSize);
		if (pageNo > totalPageCount) {
			if (totalPageCount > 0) {
				pageNo = totalPageCount;
			}

		}

		int start = (pageNo - 1) * pageSize;

		for (int i = start; i < Math.min(pageNo * pageSize, count); i++) {
			list.add(docs.doc(i));
		}

		return new Page(start, count, pageSize, list);
	}

	/**
	 * 取总页数.
	 */
	private int getTotalPageCount(int recordCount, int pageSize) {
		if (recordCount % pageSize == 0) {
			return recordCount / pageSize;
		} else {
			return recordCount / pageSize + 1;
		}
	}
}
