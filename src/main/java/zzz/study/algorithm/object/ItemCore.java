package zzz.study.algorithm.object;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemCore {
  private String id;
  private String num;

  private Item item;

  private ItemPrice itemPrice;

  private List<ItemPriceChangeLog> itemPriceChangeLogs;

}
