package cc.lovesq.study.test

import zzz.study.datastructure.map.TransferUtil

import static zzz.study.datastructure.map.TransferUtil.*

/**
 * Created by shuqin on 17/12/31.
 */
class TransferUtilTest {

    static void main(String[] args) {

        [null, "", " "].each {
            assert "" == camelToUnderline(it)
        }
        ["isBuyGoods": "is_buy_goods", "feeling": "feeling", "G":"G", "GG": "GG"].each {
            key, value -> assert camelToUnderline(key) == value
        }

        [null, "", " "].each {
            assert "" == underlineToCamel(it)
        }
        ["is_buy_goods": "isBuyGoods", "feeling": "feeling", "b":"b", "_b":"B"].each {
            key, value -> assert underlineToCamel(key) == value
        }

        def amap = ["code": 200,
                    "msg": "successful",
                    "data": [
                            "total": 2,
                            "list": [
                                    ["isBuyGoods": "a little", "feeling": ["isHappy": "ok"]],
                                    ["isBuyGoods": "ee", "feeling": ["isHappy": "haha"]],
                            ],
                            "extraInfo": [
                                    "totalFee": 1500, "totalTime": "3d",
                                    "nestInfo": [
                                            "travelDestination": "xiada",
                                            "isIgnored": true
                                    ],
                                    "buyList": ["Food","Dress","Daily"]
                            ]
                    ],
                    "extendInfo": [
                            "involveNumber": "40",
                    ]
        ]
        def resultMap = [:]
        def ignoreSets = new HashSet()
        ignoreSets.add("isIgnored")
        tranferKeyToUnderline(amap, resultMap, ignoreSets)
        println(resultMap)

        def resultMap2 = tranferKeyToUnderline2(amap, ignoreSets)
        println(resultMap2)

        def resultMap3 = generalMapProcess(amap, TransferUtil.&camelToUnderline, ignoreSets)
        println(resultMap3)

        def resultMap4 = generalMapProcess(resultMap3, TransferUtil.&underlineToCamel, ignoreSets)
        println(resultMap4)


    }
}
