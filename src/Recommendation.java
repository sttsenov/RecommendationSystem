import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Recommendation {
    /**
     * Returns top five recommended contents based on the users input
     * @param contents all the available contents
     * @param typePref users preference of type
     * @param brandPref users preference of brand
     * @return list of five names that the system recommends
     */
    public static List<String> getRecommendations(List<Collection> contents, Map<String, String> typePref, Map<String, String> brandPref){
               List<Collection> recommendations = new ArrayList<>();

        List<String> allBrands = contents.stream().map(Collection::getBrand).distinct().collect(Collectors.toList());
        List<String> allTypes = contents.stream().map(Collection::getType).distinct().collect(Collectors.toList());

        List<Collection> filteredContents = contents.stream()
                .filter(cnt -> compareYear(cnt.getAired()) && checkAvailability(cnt.getAvailability()))
                .collect(Collectors.toList());

        allBrands.forEach(brand -> {
            if (!brandPref.containsKey(brand)){
                brandPref.put(brand, "no opinion");
            }
        });

        allTypes.forEach(type -> {
            if (!typePref.containsKey(type)){
                typePref.put(type, "no opinion");
            }
        });

        List<String> favTypes = getFavorites(typePref);
        List<String> favBrand = getFavorites(brandPref);

        List<Collection> filretedByTypeCnt = new ArrayList<Collection>();

        for (int i = 0; i < favTypes.size(); i++) {
            for (int j = 0; j < filteredContents.size(); j++) {
                if(filteredContents.get(j).getType().equals(favTypes.get(i))){
                    filretedByTypeCnt.add(filteredContents.get(j));
                }
            }
        }


        for (int i = 0; i < favBrand.size(); i++) {
            for (int j = 0; j < filretedByTypeCnt.size(); j++) {
                if(filretedByTypeCnt.get(j).getBrand().equals(favBrand.get(i))){
                    recommendations.add(filretedByTypeCnt.get(j));
                }
            }
        }

        recommendations.sort(((Comparator<Collection>) Collection::compareTo).reversed());

        List<String> topFiveRecommendations = recommendations.stream().map(rec -> rec.getName()).limit(5).collect(Collectors.toList());

        return topFiveRecommendations;
    }

    /**
     * Compares the air date of the content with the desired date. If the content is aired before the desired date
     * then it returns false
     * @param aired date of airing
     * @return true if it was aired on the specific date or after
     */
    public static boolean compareYear(String aired){
        LocalDateTime date = LocalDateTime.parse(aired);
        LocalDateTime threshold = LocalDateTime.parse("2012-01-01T11:25:25");

        return (date.compareTo(threshold) > 0) || (date.compareTo(threshold) == 0);
    }

    /**
     * Checks if the contents are available in the desired country
     * @param countries countries to check from
     * @return true if the desired country is in the array
     */
    public static boolean checkAvailability(String[] countries){
        return Arrays.stream(countries).anyMatch(country -> country.equals("US"));
    }

    /**
     * Gets a map and turns its String values to Integer values so that they can be compared
     * @param pref map of strings
     * @return map of string and integer
     */
    public static Map<String, Integer> mapPref(Map<String, String> pref){
        Map<String, Integer> numberedPref = new HashMap<String, Integer>();

        pref.forEach((k, v) -> {
            if (v.equals("loves")) {
                numberedPref.put(k, 20);
            } else if (v.equals("likes")){
                numberedPref.put(k, 10);
            } else if (v.equals("no opinion")) {
                numberedPref.put(k, 0);
            } else if (v.equals("dislikes")) {
                numberedPref.put(k, -10);
            }
        });

        return numberedPref;
    }

    /**
     * Does the process of turning a map values to integer and then compares them and returns a list of sorted keys
     * @param opinion brand or type
     * @return sorted names of brands or types
     */
    public static List<String> getFavorites(Map<String, String> opinion){
        Map<String, Integer> prefs = mapPref(opinion);
        List<String> favorites = sortByValue(prefs);

        return favorites;
    }

    /**
     * Sorts a Map by Value and then adds the Keys to a List of Strings. Used to get the most liked by the user brands and types
     * @param map map of brands or types with their score
     * @param <K> name of brand or type
     * @param <V> score of brand or type
     * @return list of sorted brands or types
     */
    public static <K, V extends Comparable<? super V>> List<String> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());

        List<String> result = new ArrayList<>();
        for (Map.Entry<K, V> entry : list) {
            result.add(entry.getKey().toString());
        }

        return result;
    }
}
