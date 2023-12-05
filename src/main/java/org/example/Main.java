package org.example;

import java.util.*;


 class Almanac {
    static class PosMap {
        List<Long> dest = new ArrayList<>();
        List<Long> source = new ArrayList<>();
        List<Long> r = new ArrayList<>();



        long NextPos(long c) {
            for (int i = 0; i < source.size(); i++) {
                long sourceVal = source.get(i);
                long rVal = r.get(i);
                long destVal = dest.get(i);
                if (c >= sourceVal && c <= sourceVal + rVal) {
                    return (destVal + (c - sourceVal));
                }
            }
            return c;
        }
    }

     List<Long> seeds;
     List<Long> seedLoc;
     PosMap seedToSoil;
     PosMap soilToFertilizer;
     PosMap fertilizerToWater;
     PosMap waterToLight;
     PosMap lightToTemp;
     PosMap tempToHumidity;
     PosMap humidityToLoc;

    public Almanac() {
        seeds = new ArrayList<>();
        seedLoc = new ArrayList<>();
        seedToSoil = new PosMap();
        soilToFertilizer = new PosMap();
        fertilizerToWater = new PosMap();
        waterToLight = new PosMap();
        lightToTemp = new PosMap();
        tempToHumidity = new PosMap();
        humidityToLoc = new PosMap();
    }

    public int part1(String input) {
        String[] data = input.split("\n\n");
        Almanac almanac = new Almanac();
        almanac.seeds = parseSeeds(data[0]);
        almanac.seedToSoil = parseMap(data[1]);
        almanac.soilToFertilizer = parseMap(data[2]);
        almanac.fertilizerToWater = parseMap(data[3]);
        almanac.waterToLight = parseMap(data[4]);
        almanac.lightToTemp = parseMap(data[5]);
        almanac.tempToHumidity = parseMap(data[6]);
        almanac.humidityToLoc = parseMap(data[7]);
        almanac.seedsToLocs();
        int min = Math.toIntExact(Collections.min(almanac.seedLoc));
        return min;
    }

    public int part2(String input) {
        String[] data = input.split("\n\n");
        Almanac almanac = new Almanac();
        almanac.seeds = parseSeeds(data[0]);
        almanac.seedToSoil = parseMap(data[1]);
        almanac.soilToFertilizer = parseMap(data[2]);
        almanac.fertilizerToWater = parseMap(data[3]);
        almanac.waterToLight = parseMap(data[4]);
        almanac.lightToTemp = parseMap(data[5]);
        almanac.tempToHumidity = parseMap(data[6]);
        almanac.humidityToLoc = parseMap(data[7]);
        almanac.seedRangeToLocs();
        int min = Math.toIntExact(Collections.min(almanac.seedLoc));
        return min;
    }

    private List<Long> parseSeeds(String seeds) {
        seeds = seeds.split(": ")[1];
        String[] seedsSplit = seeds.split(" ");
        List<Long> seedList = new ArrayList<>();
        for (String seed : seedsSplit) {
            if (!seed.isEmpty()) {
                seedList.add(Long.parseLong(seed));
            }
        }
        return seedList;
    }

    private PosMap parseMap(String m) {
        String[] lines = m.split("\n");
        PosMap pm = new PosMap();
        for (int i = 1; i < lines.length; i++) {
            String[] vals = lines[i].split(" ");
            long dest = Long.parseLong(vals[0]);
            long source = Long.parseLong(vals[1]);
            long r = Long.parseLong(vals[2]);
            pm.dest.add(dest);
            pm.source.add(source);
            pm.r.add(r);
        }
        return pm;
    }

    private void seedsToLocs() {
        for (long seed : seeds) {
            seedLoc.add(seedToLoc(seed));
        }
    }

    private long seedToLoc(long c) {
        c = seedToSoil.NextPos(c);
        c = soilToFertilizer.NextPos(c);
        c = fertilizerToWater.NextPos(c);
        c = waterToLight.NextPos(c);
        c = lightToTemp.NextPos(c);
        c = tempToHumidity.NextPos(c);
        c = humidityToLoc.NextPos(c);
        return c;
    }

    private void seedRangeToLocs() {
        for (int i = 0; i < seeds.size(); i += 2) {
            long seed = seeds.get(i);
            long totalSeeds = seeds.get(i + 1);
            long minLoc = Long.MAX_VALUE;
            long maxLoc = Long.MIN_VALUE;
            for (long j = seed; j < seed + totalSeeds; j++) {
                long loc = seedToLoc(j);
                if (loc < minLoc) {
                    minLoc = loc;
                }
                if (loc > maxLoc) {
                    maxLoc = loc;
                }
            }
            seedLoc.add((maxLoc - minLoc) + 1);
        }
    }
}
public class Main {
    public static void main(String[] args) {
        String input = "seeds: 2906961955 52237479 1600322402 372221628 2347782594 164705568 541904540 89745770 126821306 192539923 3411274151 496169308 919015581 8667739 654599767 160781040 3945616935 85197451 999146581 344584779\n" +
                "\n" +
                "seed-to-soil map:\n" +
                "2328388605 1716277852 240111965\n" +
                "3183744888 3056742994 1057221520\n" +
                "0 1574488136 141789716\n" +
                "886579086 0 430196980\n" +
                "141789716 2525350732 49125971\n" +
                "881053613 1956389817 5525473\n" +
                "3131936012 3013025394 43717600\n" +
                "629464378 1322898901 251589235\n" +
                "2568500570 1961915290 563435442\n" +
                "190915687 2574476703 438548691\n" +
                "3175653612 4113964514 8091276\n" +
                "1435686684 430196980 892701921\n" +
                "1316776066 4122055790 118910618\n" +
                "\n" +
                "soil-to-fertilizer map:\n" +
                "0 847324382 159395290\n" +
                "1690371026 1633839702 81823607\n" +
                "1942873068 561308539 283153025\n" +
                "702170285 347687930 213620609\n" +
                "477747447 1412279682 221560020\n" +
                "1284811016 1006719672 253212372\n" +
                "2226026093 170678435 177009495\n" +
                "3187826244 2792977617 352243190\n" +
                "4225283470 3206651416 69683826\n" +
                "2970405965 3691658787 56026212\n" +
                "1538023388 1259932044 152347638\n" +
                "1772194633 0 170678435\n" +
                "3540069434 3376298700 315360087\n" +
                "159395290 2084683431 318352157\n" +
                "915790894 1715663309 369020122\n" +
                "2792977617 4117538948 177428348\n" +
                "3026432177 3145220807 61430609\n" +
                "699307467 844461564 2862818\n" +
                "3855429521 3747684999 21637270\n" +
                "3877066791 3769322269 348216679\n" +
                "3087862786 3276335242 99963458\n" +
                "\n" +
                "fertilizer-to-water map:\n" +
                "1957494254 3160765597 68123328\n" +
                "3668612865 2055686861 40068724\n" +
                "435525860 0 215171709\n" +
                "417459051 715121035 18066809\n" +
                "3249874551 2634587998 121218972\n" +
                "3537356195 3926770979 26944785\n" +
                "409490069 215171709 7968982\n" +
                "3247878798 2456653359 1995753\n" +
                "152557237 570466650 62164110\n" +
                "0 223140691 152557237\n" +
                "3740494262 3322870672 151571624\n" +
                "4127559328 2467180030 146770000\n" +
                "2244919185 2003747275 51939586\n" +
                "1782523793 2281682898 174970461\n" +
                "829840892 2458649112 8530918\n" +
                "3415930152 2819793210 9616809\n" +
                "3451260386 2162555289 86095809\n" +
                "650697569 632630760 82490275\n" +
                "1725834205 787129181 30553919\n" +
                "2298219434 2095755585 66799704\n" +
                "4274329328 2613950030 20637968\n" +
                "2025617582 4242693061 52274235\n" +
                "3564300980 2248651098 31671137\n" +
                "2296858771 2280322235 1360663\n" +
                "3162226157 4125227747 85652641\n" +
                "1564565599 1911070479 92676796\n" +
                "3892065886 3691277537 235493442\n" +
                "3371093523 1848297376 44836629\n" +
                "1187663862 1437436130 376901737\n" +
                "1689179378 2755806970 36654827\n" +
                "2077891817 1238471779 167027368\n" +
                "2365019138 2792461797 27331413\n" +
                "2678412296 3953715764 63025182\n" +
                "2741437478 817683100 420788679\n" +
                "2461577055 3474442296 216835241\n" +
                "787129181 3228888925 21340999\n" +
                "3595972117 3250229924 72640748\n" +
                "838371810 2829410019 331355578\n" +
                "3425546961 4085967450 25713425\n" +
                "1657242395 1405499147 31936983\n" +
                "3708681589 4210880388 31812673\n" +
                "1756388124 4111680875 13546872\n" +
                "1169727388 1893134005 17936474\n" +
                "2392350551 4016740946 69226504\n" +
                "808470180 1826926664 21370712\n" +
                "218943670 379920251 190546399\n" +
                "214721347 375697928 4222323\n" +
                "1769934996 1814337867 12588797\n" +
                "\n" +
                "water-to-light map:\n" +
                "3411019791 3038265544 83843899\n" +
                "1902872961 1862724448 39203216\n" +
                "3968365807 2753393749 138464731\n" +
                "1729762115 1614300400 173110846\n" +
                "3494863690 3122109443 473502117\n" +
                "423365393 445887946 9248183\n" +
                "2869853730 3595611560 11542547\n" +
                "910597576 2054510840 169422772\n" +
                "609563085 2589844190 163549559\n" +
                "2522382340 1299215911 3599854\n" +
                "2525982194 609563085 139503131\n" +
                "131438599 455136129 141314541\n" +
                "4106830538 3607154107 188136758\n" +
                "2106420951 2290083024 299761166\n" +
                "2406182117 966254850 82053377\n" +
                "1157660094 1302815765 172746664\n" +
                "97774358 248386611 33664241\n" +
                "432613576 282050852 163837094\n" +
                "2835557666 1794281538 34296064\n" +
                "1953837775 2043524684 10986156\n" +
                "1964823931 1901927664 141597020\n" +
                "2881396277 749066216 217188634\n" +
                "3404149499 1787411246 6870292\n" +
                "1330406758 3795290865 240494284\n" +
                "773112644 4035785149 137484932\n" +
                "3282452284 4173270081 121697215\n" +
                "2665485325 3001382926 36882618\n" +
                "272753140 97774358 150612253\n" +
                "0 38271406 59502952\n" +
                "3098584911 1115348538 183867373\n" +
                "1570901042 2938014447 31884700\n" +
                "1942076177 1602538802 11761598\n" +
                "2488235494 1828577602 34146846\n" +
                "1080020348 2969899147 31483779\n" +
                "1602785742 1475562429 126976373\n" +
                "1111504127 2891858480 46155967\n" +
                "2768517355 1048308227 67040311\n" +
                "59502952 0 38271406\n" +
                "2702367943 2223933612 66149412\n" +
                "\n" +
                "light-to-temperature map:\n" +
                "2325040309 318068442 41526915\n" +
                "3863810956 4139509244 32531846\n" +
                "3695021342 4093645836 45863408\n" +
                "3176344157 3053768010 518677185\n" +
                "2214303174 207331307 110737135\n" +
                "1166404663 0 207331307\n" +
                "3896342802 3761428671 206212717\n" +
                "216675148 359595357 47363132\n" +
                "4102555519 3967641388 126004448\n" +
                "264038280 406958489 888098871\n" +
                "4228559967 3572445195 66407329\n" +
                "1152137151 1295057360 14267512\n" +
                "3053768010 3638852524 122576147\n" +
                "1373735970 1526000020 840567204\n" +
                "0 1309324872 216675148\n" +
                "3740884750 4172041090 122926206\n" +
                "\n" +
                "temperature-to-humidity map:\n" +
                "0 1391213880 85308204\n" +
                "3458067104 3613119976 31521054\n" +
                "1325453299 1240145095 151068785\n" +
                "4139914424 3458067104 155052872\n" +
                "1271510819 1186202615 53942480\n" +
                "85308204 0 1186202615\n" +
                "3489588158 3644641030 650326266\n" +
                "\n" +
                "humidity-to-location map:\n" +
                "167312469 2495357077 210182350\n" +
                "1429747769 3808500825 68802537\n" +
                "2550869359 1829966489 80945905\n" +
                "1243007130 3610450429 186740639\n" +
                "1735537822 990200552 68491638\n" +
                "2426758858 2125855270 111895863\n" +
                "1637338839 2027656287 98198983\n" +
                "3109292740 3306673452 735862\n" +
                "3647611593 2386885260 96257179\n" +
                "3910495658 3919394548 32743716\n" +
                "3860612665 3289982755 16690697\n" +
                "3743868772 1910912394 116743893\n" +
                "1498550306 2705539427 138788533\n" +
                "0 1662654020 167312469\n" +
                "2631815264 512723076 477477476\n" +
                "836912419 1370893553 291760467\n" +
                "2271993218 3307409314 154765640\n" +
                "4136363029 3910495658 8898890\n" +
                "377494819 3797191068 11309757\n" +
                "1804029460 1058692190 209661300\n" +
                "836150842 3517760656 761577\n" +
                "3110028602 3518522233 91928196\n" +
                "2538654721 2483142439 12214638\n" +
                "687016715 2237751133 149134127\n" +
                "3201956798 2844327960 445654795\n" +
                "1128672886 1268353490 102540063\n" +
                "2057482281 0 214510937\n" +
                "388804576 214510937 298212139\n" +
                "1231212949 3505966475 11794181\n" +
                "2013690760 3462174954 43791521\n" +
                "3943239374 3952138264 193123655";

        Almanac almanac = new Almanac();

        System.out.println("Part 1: " + almanac.part1(input));
        System.out.println("Part 2: " + almanac.part2(input));
    }
}
