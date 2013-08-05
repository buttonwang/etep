package com.ambow.studyflow.exception;

import java.util.Collections;
import java.util.List;

import com.ambow.studyflow.xml.Problem;

public class AsfException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	protected List problems = null;

	public AsfException(List problems) {
		super(problems.toString());
		this.problems = problems;
	}

	public AsfException(String msg) {
		this(Collections.singletonList(new Problem(Problem.LEVEL_ERROR, msg)));
	}

	public AsfException(String msg, Throwable e) {
		this(Collections
				.singletonList(new Problem(Problem.LEVEL_ERROR, msg, e)));
	}

	public List getProblems() {
		return problems;
	}
}
