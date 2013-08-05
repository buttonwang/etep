package com.ambow.studyflow.xml;

import java.io.Serializable;

public interface ProblemListener extends Serializable {

  void addProblem(Problem problem);
}
