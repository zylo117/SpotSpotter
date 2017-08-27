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
 *            ����������
 * @param <S>
 *            ����������
 */

public class Ransac<T, S> {
	private List<S> parameters = null;
	private ParameterEstimator<T, S> paramEstimator;
	private boolean[] bestVotes;
	private int numForEstimate;
	private double maximalOutlierPercentage;

	/**
	 * @return �����ڵ㼯
	 */
	public boolean[] getBestVotes() {
		return bestVotes;
	}

	/**
	 * @return ����ģ�Ͳ���
	 */
	public List<S> getParameters() {
		return parameters;
	}

	/**
	 * Ransac����Ĺ��췽��
	 *
	 * @param paramEstimator
	 *            ��ʹ�õĲ���������
	 * @param numForEstimate
	 *            ����ģ�Ͳ����������С������
	 * @param maximalOutlierPercentage
	 *            �������ٷֱ�
	 */
	public Ransac(ParameterEstimator<T, S> paramEstimator, int numForEstimate, double maximalOutlierPercentage) {
		this.paramEstimator = paramEstimator;
		this.numForEstimate = numForEstimate;
		this.maximalOutlierPercentage = maximalOutlierPercentage;
	}

	/**
	 * ִ��RANSAC�㷨�ķ���
	 *
	 * @param data
	 *            ��������
	 * @param desiredProbabilityForNoOutliers
	 *            ����Ҫ�ľ���
	 * @return ��������µ��ڵ�ٷֱ�
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
			// ���ѡȡ����
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
			// ���Ӽ�δѡ������ִ�в���
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
		// �Ե�ǰ�����Ӽ�ʹ����С���˷��������Ų���
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
	* ģ�͹������ӿ�
	*
	* @param <T>
	*            ����������
	* @param <S>
	*            ����������
	*/
	public interface ParameterEstimator<T, S> {
		/**
		 * ִ��׼ȷ�������Ƶķ���
		 *
		 * @param data
		 *            ���ڹ��Ƶ���������
		 * @return ģ�Ͳ����б�
		 */
		public List<S> estimate(List<T> data);

		/**
		 * ִ����С���˷����Ƶķ���
		 *
		 * @param data
		 *            ���ڹ��Ƶ���������
		 * @return ģ�Ͳ����б�
		 */
		public List<S> leastSquaresEstimate(List<T> data);

		/**
		 * ���������Ƿ����ģ�Ͳ����ķ���
		 *
		 * @param parameters
		 *            ģ�Ͳ���
		 * @param data
		 *            ��������
		 */
		public boolean agree(List<S> parameters, T data);
	}
}
