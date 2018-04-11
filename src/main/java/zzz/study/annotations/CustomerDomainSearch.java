package zzz.study.annotations;

import java.util.Arrays;

/**
 * Created by shuqin on 18/4/11.
 */
public class CustomerDomainSearch {

  public static void main(String[] args) {
    CustomerDomain customerDomain = new CustomerDomain();
    customerDomain.setShopId(1L);
    customerDomain.setOrderNo("E201711081202890002");
    customerDomain.setOrderType(Arrays.asList(10));
    customerDomain.setState(Arrays.asList(5,6,100));
    DomainSearch search = customerDomain;
    System.out.println(search.toEsQuery());

    CustomerDomain customerDomain2 = new CustomerDomain();
    customerDomain2.setOrderNo("E201711081202890002");
    customerDomain2.setState(Arrays.asList(5,6,100));
    DomainSearch search2 = customerDomain2;
    System.out.println(search2.toEsQuery());
  }

}
