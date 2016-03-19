package zzz.study.algorithm.problems;

import zzz.study.algorithm.permutation.Combination;
import zzz.study.algorithm.runtime.Command;


public class CombinationCommand extends Combination implements Command {

	public CombinationCommand(int n) {
		super(n);
	}

	public void run() {
		solution();
	}

}
