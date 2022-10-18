package in.one2n.studentgrading.util;

import io.micronaut.data.model.Pageable;

public class PageableUtils implements Pageable {

  private int number;
  private int size;

  public PageableUtils(int size, int number) {
    this.size = size;
    this.number = number;
  }

  @Override
  public int getNumber() {
    return number;
  }

  @Override
  public int getSize() {
    return size;
  }
}
