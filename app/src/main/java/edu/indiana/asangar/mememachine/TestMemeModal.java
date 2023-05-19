package edu.indiana.asangar.mememachine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TestMemeModal {

    /** Fetches memes in MemeModal - Use in HomeFragment.java to test API */
    public ArrayList<IMemeModal> getMemeModal(String subredditName, int count) {
        ArrayList<IMemeModal> modalArrayList;

        // initialize array list
        modalArrayList = new ArrayList<>();

        try {
            String jsonString = "{\n" +
                    "\t\"count\": 6,\n" +
                    "\t\"memes\": [{\n" +
                    "\t\t\t\"postLink\": \"https://redd.it/jictqq\",\n" +
                    "\t\t\t\"subreddit\": \"dankmemes\",\n" +
                    "\t\t\t\"title\": \"Say sike\",\n" +
                    "\t\t\t\"url\": \"https://i.redd.it/j6wu6o9ncfv51.gif\",\n" +
                    "\t\t\t\"nsfw\": false,\n" +
                    "\t\t\t\"spoiler\": false,\n" +
                    "\t\t\t\"author\": \"n1GG99\",\n" +
                    "\t\t\t\"ups\": 72823,\n" +
                    "\t\t\t\"preview\": [\n" +
                    "\t\t\t\t\"https://preview.redd.it/j6wu6o9ncfv51.gif?width=108&crop=smart&format=png8&s=3b110a4d83a383b7bfebaf09ea60d89619cddfb3\",\n" +
                    "\t\t\t\t\"https://preview.redd.it/j6wu6o9ncfv51.gif?width=216&crop=smart&format=png8&s=ba5808992b3245a6518dfe759cbe4af24e042f2d\",\n" +
                    "\t\t\t\t\"https://preview.redd.it/j6wu6o9ncfv51.gif?width=320&crop=smart&format=png8&s=7567bb64e639223e3603236f774eeca149551313\"\n" +
                    "\t\t\t]\n" +
                    "\t\t},\n" +
                    "\t\t{\n" +
                    "\t\t\t\"postLink\": \"https://redd.it/jilgdw\",\n" +
                    "\t\t\t\"subreddit\": \"dankmemes\",\n" +
                    "\t\t\t\"title\": \"I forgot how hard it is to think of a title\",\n" +
                    "\t\t\t\"url\": \"https://i.redd.it/jk12rq8nrhv51.jpg\",\n" +
                    "\t\t\t\"nsfw\": false,\n" +
                    "\t\t\t\"spoiler\": false,\n" +
                    "\t\t\t\"author\": \"TheRealKyJoe01\",\n" +
                    "\t\t\t\"ups\": 659,\n" +
                    "\t\t\t\"preview\": [\n" +
                    "\t\t\t\t\"https://preview.redd.it/jk12rq8nrhv51.jpg?width=108&crop=smart&auto=webp&s=d5d3fe588ccff889e61fca527c2358e429845b80\",\n" +
                    "\t\t\t\t\"https://preview.redd.it/jk12rq8nrhv51.jpg?width=216&crop=smart&auto=webp&s=b560b78301afd8c173f8c702fbd791214c1d7f61\",\n" +
                    "\t\t\t\t\"https://preview.redd.it/jk12rq8nrhv51.jpg?width=320&crop=smart&auto=webp&s=3cd427240b2185a3691a818774214fd2a0de124d\",\n" +
                    "\t\t\t\t\"https://preview.redd.it/jk12rq8nrhv51.jpg?width=640&crop=smart&auto=webp&s=1142cc19a746b8b5d8335679d1d36127f4a677b9\"\n" +
                    "\t\t\t]\n" +
                    "\t\t},\n" +
                    "\t\t{\n" +
                    "\t\t\t\"postLink\": \"https://redd.it/jiovfz\",\n" +
                    "\t\t\t\"subreddit\": \"dankmemes\",\n" +
                    "\t\t\t\"title\": \"*leaves call*\",\n" +
                    "\t\t\t\"url\": \"https://i.redd.it/f7ibqp1dmiv51.gif\",\n" +
                    "\t\t\t\"nsfw\": false,\n" +
                    "\t\t\t\"spoiler\": false,\n" +
                    "\t\t\t\"author\": \"Spartan-Yeet\",\n" +
                    "\t\t\t\"ups\": 3363,\n" +
                    "\t\t\t\"preview\": [\n" +
                    "\t\t\t\t\"https://preview.redd.it/f7ibqp1dmiv51.gif?width=108&crop=smart&format=png8&s=02b12609100c14f55c31fe046f413a9415804d62\",\n" +
                    "\t\t\t\t\"https://preview.redd.it/f7ibqp1dmiv51.gif?width=216&crop=smart&format=png8&s=8da35457641a045e88e42a25eca64c14a6759f82\",\n" +
                    "\t\t\t\t\"https://preview.redd.it/f7ibqp1dmiv51.gif?width=320&crop=smart&format=png8&s=f2250b007b8252c7063b8580c2aa72c5741766ae\",\n" +
                    "\t\t\t\t\"https://preview.redd.it/f7ibqp1dmiv51.gif?width=640&crop=smart&format=png8&s=6cd99df5e58c976bc115bd080a1e6afdbd0d71e7\"\n" +
                    "\t\t\t]\n" +
                    "\t\t},\n" +
                    "\t\t{\n" +
                    "\t\t\t\"postLink\": \"https://redd.it/jhr5lf\",\n" +
                    "\t\t\t\"subreddit\": \"wholesomememes\",\n" +
                    "\t\t\t\"title\": \"Every time I visit\",\n" +
                    "\t\t\t\"url\": \"https://i.redd.it/hsyyeb87v7v51.jpg\",\n" +
                    "\t\t\t\"nsfw\": false,\n" +
                    "\t\t\t\"spoiler\": false,\n" +
                    "\t\t\t\"author\": \"pak_choy\",\n" +
                    "\t\t\t\"ups\": 1660,\n" +
                    "\t\t\t\"preview\": [\n" +
                    "\t\t\t\t\"https://preview.redd.it/hsyyeb87v7v51.jpg?width=108&crop=smart&auto=webp&s=b76ddb91f212b2e304cad2cd9c5b71a6ddca832c\",\n" +
                    "\t\t\t\t\"https://preview.redd.it/hsyyeb87v7v51.jpg?width=216&crop=smart&auto=webp&s=2bd0b104fd0825afc15d9faa7977c6801e6dae0b\",\n" +
                    "\t\t\t\t\"https://preview.redd.it/hsyyeb87v7v51.jpg?width=320&crop=smart&auto=webp&s=7625c69e144c9cb187dd0be88f541918aca5cedd\",\n" +
                    "\t\t\t\t\"https://preview.redd.it/hsyyeb87v7v51.jpg?width=640&crop=smart&auto=webp&s=e933f956e01d62810e68f12ed8b26a8178ecbb0f\"\n" +
                    "\t\t\t]\n" +
                    "\t\t},\n" +
                    "\t\t{\n" +
                    "\t\t\t\"postLink\": \"https://redd.it/ji1riw\",\n" +
                    "\t\t\t\"subreddit\": \"wholesomememes\",\n" +
                    "\t\t\t\"title\": \"It makes me feel good.\",\n" +
                    "\t\t\t\"url\": \"https://i.redd.it/xuzd77yl8bv51.png\",\n" +
                    "\t\t\t\"nsfw\": false,\n" +
                    "\t\t\t\"spoiler\": false,\n" +
                    "\t\t\t\"author\": \"polyesterairpods\",\n" +
                    "\t\t\t\"ups\": 306,\n" +
                    "\t\t\t\"preview\": [\n" +
                    "\t\t\t\t\"https://preview.redd.it/xuzd77yl8bv51.png?width=108&crop=smart&auto=webp&s=9a0376741fbda988ceeb7d96fdec3982f102313e\",\n" +
                    "\t\t\t\t\"https://preview.redd.it/xuzd77yl8bv51.png?width=216&crop=smart&auto=webp&s=ee2f287bf3f215da9c1cd88c865692b91512476d\",\n" +
                    "\t\t\t\t\"https://preview.redd.it/xuzd77yl8bv51.png?width=320&crop=smart&auto=webp&s=88850d9155d51f568fdb0ad527c94d556cd8bd70\",\n" +
                    "\t\t\t\t\"https://preview.redd.it/xuzd77yl8bv51.png?width=640&crop=smart&auto=webp&s=b7418b023b2f09cdc189a55ff1c57d531028bc3e\"\n" +
                    "\t\t\t]\n" +
                    "\t\t},\n" +
                    "\t\t{\n" +
                    "\t\t\t\"postLink\": \"https://redd.it/jibifc\",\n" +
                    "\t\t\t\"subreddit\": \"wholesomememes\",\n" +
                    "\t\t\t\"title\": \"It really feels like that\",\n" +
                    "\t\t\t\"url\": \"https://i.redd.it/vvpbl29prev51.jpg\",\n" +
                    "\t\t\t\"nsfw\": false,\n" +
                    "\t\t\t\"spoiler\": false,\n" +
                    "\t\t\t\"author\": \"lolthebest\",\n" +
                    "\t\t\t\"ups\": 188,\n" +
                    "\t\t\t\"preview\": [\n" +
                    "\t\t\t\t\"https://preview.redd.it/vvpbl29prev51.jpg?width=108&crop=smart&auto=webp&s=cf64f01dfaca5f41c2e87651e4b0e321e28fa47c\",\n" +
                    "\t\t\t\t\"https://preview.redd.it/vvpbl29prev51.jpg?width=216&crop=smart&auto=webp&s=33acdf7ed7d943e1438039aa71fe9295ee2ff5a0\",\n" +
                    "\t\t\t\t\"https://preview.redd.it/vvpbl29prev51.jpg?width=320&crop=smart&auto=webp&s=6a0497b998bd9364cdb97876aa54c147089270da\",\n" +
                    "\t\t\t\t\"https://preview.redd.it/vvpbl29prev51.jpg?width=640&crop=smart&auto=webp&s=e68fbe686e92acb5977bcfc24dd57febd552afaf\",\n" +
                    "\t\t\t\t\"https://preview.redd.it/vvpbl29prev51.jpg?width=960&crop=smart&auto=webp&s=1ba690cfe8d49480fdd55c6daee6f2692e9292e7\",\n" +
                    "\t\t\t\t\"https://preview.redd.it/vvpbl29prev51.jpg?width=1080&crop=smart&auto=webp&s=44852004dba921a17ee4ade108980baab242805e\"\n" +
                    "\t\t\t]\n" +
                    "\t\t}\n" +
                    "\t]\n" +
                    "}";
            JSONObject response = null;
            try {
                response = new JSONObject(jsonString);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            int responseCount = response.getInt("count");
            JSONArray dataArray = response.getJSONArray("memes");
            for (int i = 0; i < responseCount; i++) {
                JSONObject dataObj = dataArray.getJSONObject(i);
                IMemeModal memeModal = new RedditMemeModal(dataObj);

                // below line is use to add modal class to our array list
                modalArrayList.add(memeModal);
            }
        } catch (JSONException e) {
            // handling error case.
            e.printStackTrace();
        }

        return modalArrayList;
    }
}
