// --== CS400 File Header Information ==--
// Name: Vaishnavi Deshpande
// Email: vvdeshpande@wisc.edu
// Team: LB
// Role: Data Wrangler 2
// TA: Divyanshu Saxena
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

public class Date implements Comparable<Date> {
  int day;
  int month;
  int year;

  public Date(int day, int month, int year) {
    this.day = day;
    this.month = month;
    this.year = year;
  }

  public String toString() {
    return this.month + "/" + this.day + "/" + this.year;
  }

  @Override
  public int compareTo(Date o) {
    if (this.year > o.year) {
      return 1;
    } else if (year < o.year) {
      return -1;
    } else {
      if (this.month > o.month) {
        return 1;
      } else if (this.month < o.month) {
        return -1;
      } else {
        if (this.day > o.day) {
          return 1;
        } else if (this.day < o.day) {
          return -1;
        } else {
          return 0;
        }
      }
    }
  }
}
