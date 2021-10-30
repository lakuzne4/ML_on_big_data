import breeze.linalg.{DenseMatrix, DenseVector, csvread, sum}
import java.io.{File, PrintWriter}
import breeze.stats.regression.leastSquares

import java.nio.file.Paths


object Main {
  def get_r_squared(predict_val: DenseVector[Double],
                    true_val: DenseVector[Double]): Double ={
    val e = predict_val - true_val
    val ss_res = e.dot(e)
    val avg = sum(true_val) / true_val.length
    val e_to_avg = true_val - avg
    val ss_tot = e_to_avg.dot(e_to_avg)
    val result = 1 - ss_res / ss_tot

    result
  }

  def get_data_for_model(path: String): (DenseMatrix[Double], DenseVector[Double]) =
  {
    // data loading
    val matrix = csvread(new File(
      path
    ), skipLines = 1)

    // data transforming
    val X = matrix(::, IndexedSeq(0, 1, 2, 3, 4)).toDenseMatrix
    val X_with_intercept = DenseMatrix.horzcat(X,
      DenseVector.ones[Double](X.rows).toDenseMatrix.reshape(X.rows, 1))
    val y = DenseVector(matrix(::, IndexedSeq(5)).toDenseMatrix.toArray)

    (X_with_intercept, y)
  }

  def write_to_file(vector: DenseVector[Double], path: String): Unit ={
    val pw = new PrintWriter(
      new File(path)
    )

    pw.write("Prediction\n")
    vector.foreach(row => pw.write(row.toString + "\n"))
    pw.close()

    println("Prediction written to file:")
    println(path)
  }

  def main(resources_path: String): Unit = {

    val train_path = Paths.get(resources_path, "insurance_data_train.csv").toString
    val test_path = Paths.get(resources_path, "insurance_data_test.csv").toString
    val result_path = Paths.get(resources_path, "insurance_data_prediction.csv").toString

    // getting data
    val model_train_data = get_data_for_model(path = train_path)

    // training model
    val result = leastSquares(model_train_data._1, model_train_data._2)

    // printing of results
    println("coefficients:")
    for (i <- 0 to 4) {
      println(result.coefficients.data(i))
    }
    println("intercept:")
    println(result.coefficients.data(5))

    // prediction by linear regression on train data (validation of model)

    val train_prediction = model_train_data._1  * result.coefficients

    println("R-squared on train data:")
    println(get_r_squared(train_prediction, model_train_data._2))

    // get test data
    val model_test_data = get_data_for_model(path = test_path)

    val test_prediction = model_test_data._1 * result.coefficients

    println("R-squared on test data:")
    println(get_r_squared(test_prediction, model_test_data._2))


    write_to_file(test_prediction, result_path)

  }
}

Main.main("D:\\MADE_homeworks\\big_data\\for_hw3\\resources")
