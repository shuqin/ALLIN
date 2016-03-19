package zzz.study.algorithm.object;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ItemCore {
    private String id;
    private String num;

    private Item item;

    private ItemPrice itemPrice;

    private List<ItemPriceChangeLog> itemPriceChangeLogs;

}
