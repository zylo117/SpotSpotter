package pers.zylo117.spotspotter.toolbox.mathBox;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

// RANdom SAmple Cosensus

/**
 *
 * @param <T>
 *            样本的类型
 * @param <S>
 *            参数的类型
 */

public class Ransac<T, S> {
	private List<S> parameters = null;
	private ParameterEstimator<T, S> paramEstimator;
	private boolean[] bestVotes;
	private int numForEstimate;
	private double maximalOutlierPercentage;

	/**
	 * @return 最优内点集
	 */
	public boolean[] getBestVotes() {
		return bestVotes;
	}

	/**
	 * @return 最优模型参数
	 */
	public List<S> getParameters() {
		return parameters;
	}

	/**
	 * Ransac对象的构造方法
	 *
	 * @param paramEstimator
	 *            所使用的参数估计器
	 * @param numForEstimate
	 *            估计模型参数所需的最小样本数
	 * @param maximalOutlierPercentage
	 *            外点的最大百分比
	 */
	public Ransac(ParameterEstimator<T, S> paramEstimator, int numForEstimate, double maximalOutlierPercentage) {
		this.paramEstimator = paramEstimator;
		this.numForEstimate = numForEstimate;
		this.maximalOutlierPercentage = maximalOutlierPercentage;
	}

	/**
	 * 执行RANSAC算法的方法
	 *
	 * @param data
	 *            样本集合
	 * @param desiredProbabilityForNoOutliers
	 *            所需要的精度
	 * @return 最优情况下的内点百分比
	 */
	public double compute(List<T> data, double desiredProbabilityForNoOutliers) {
		int dataSize = data.size();
		if (dataSize < numForEstimate || maximalOutlierPercentage >= 1.0) {
			return 0.0;
		}
		List<T> exactedData = new ArrayList<T>();
		List<T> leastSqData;
		List<S> exactedParams;
		int bestSize, curSize, tryTimes;
		bestVotes = new boolean[dataSize];
		boolean[] curVotes = new boolean[dataSize];
		boolean[] notChosen = new boolean[dataSize];
		Set<int[]> chosenSubSets = new HashSet<int[]>();
		int[] curSubSetIndexes;
		double outlierPercentage = maximalOutlierPercentage;
		double numerator = Math.log(1.0 - desiredProbabilityForNoOutliers);
		double denominator = Math.log(1 - Math.pow(1 - maximalOutlierPercentage, numForEstimate));
		if (parameters != null) {
			parameters.clear();
		} else {
			parameters = new ArrayList<S>();
		}
		bestSize = -1;
		Random random = new Random(new Date().getTime());
		tryTimes = (int) Math.round(numerator / denominator);
		for (int i = 0; i < tryTimes; i++) {
			for (int j = 0; j < notChosen.length; j++) {
				notChosen[j] = true;
			}
			curSubSetIndexes = new int[numForEstimate];
			exactedData.clear();
			// 随机选取样本
			for (int j = 0; j < numForEstimate; j++) {
				int selectedIndex = random.nextInt(dataSize - j);
				int k, l;
				for (k = 0, l = -1; k < dataSize && l < selectedIndex; k++) {
					if (notChosen[k]) {
						l++;
					}
				}
				k--;
				exactedData.add(data.get(k));
				notChosen[k] = false;
			}
			for (int j = 0, k = 0; j < dataSize; j++) {
				if (!notChosen[j]) {
					curSubSetIndexes[k] = j;
					k++;
				}
			}
			// 若子集未选区过则执行测试
			if (chosenSubSets.add(curSubSetIndexes)) {
				exactedParams = paramEstimator.estimate(exactedData);
				curSize = 0;
				for (int j = 0; j < notChosen.length; j++) {
					curVotes[j] = false;
				}
				for (int j = 0; j < dataSize; j++) {
					if (paramEstimator.agree(exactedParams, data.get(j))) {
						curVotes[j] = true;
						curSize++;
					}
				}
				if (curSize > bestSize) {
					bestSize = curSize;
					System.arraycopy(curVotes, 0, bestVotes, 0, dataSize);
				}
				outlierPercentage = 1.0 - (double) curSize / (double) dataSize;
				if (outlierPercentage < maximalOutlierPercentage) {
					maximalOutlierPercentage = outlierPercentage;
					denominator = Math.log(1 - Math.pow(1 - maximalOutlierPercentage, numForEstimate));
					tryTimes = (int) Math.round(numerator / denominator);
				}
			} else {
				i--;
			}
		}
		chosenSubSets.clear();
		// 对当前最优子集使用最小二乘法计算最优参数
		leastSqData = new ArrayList<T>();
		for (int i = 0; i < dataSize; i++) {
			if (bestVotes[i]) {
				leastSqData.add(data.get(i));
			}
		}
		parameters = paramEstimator.leastSquaresEstimate(leastSqData);
		return (double) bestSize / (double) dataSize;
	}

	
	/**
	* 模型估计器接口
	*
	* @param <T>
	*            样本的类型
	* @param <S>
	*            参数的类型
	*/
	public interface ParameterEstimator<T, S> {
		/**
		 * 执行准确参数估计的方法
		 *
		 * @param data
		 *            用于估计的样本集合
		 * @return 模型参数列表
		 */
		public List<S> estimate(List<T> data);

		/**
		 * 执行最小二乘法估计的方法
		 *
		 * @param data
		 *            用于估计的样本集合
		 * @return 模型参数列表
		 */
		public List<S> leastSquaresEstimate(List<T> data);

		/**
		 * 测试样本是否符合模型参数的方法
		 *
		 * @param parameters
		 *            模型参数
		 * @param data
		 *            待测样本
		 */
		public boolean agree(List<S> parameters, T data);
	}
}
