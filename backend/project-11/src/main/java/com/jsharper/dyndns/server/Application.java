package com.jsharper.dyndns.server;

import org.apache.commons.io.FileUtils;
import org.javatuples.Pair;
import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.StreamSupport;

public class Application {
    private String json;

    public Application() throws Exception {
        var pathLocal = Paths.get("src", "main", "resources", "data", "videos.json");

        this.json = new String(Files.readAllBytes(pathLocal));

        var array = new JSONArray(this.json);

        var stream = StreamSupport.stream(array.spliterator(), false);

        var paths = stream.map((p) -> {
            try {
                var uri = URI.create(p.toString());
                var url = uri.toURL();
                var path = Paths.get(url.getPath());
                return new Pair<>(url, path.getFileName());
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }).toList();
        paths.forEach(System.out::println);
    }

  public  static void main(String[] args) throws Exception {

        Path resourceDirectory = Paths.get("src", "main", "resources", "downloads");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();
        // var json =.getResourceAsStream("data/videos.json");
        var a = new Application().json;
        System.out.println("Hello World" + absolutePath);
        // System.out.println(a);

        //var output = absolutePath +"//cloud.mp4";
        // var link = "https://toppng.com/uploads/preview/black-cat-yellow-eyes-png-115259561504sgku9szvq.png";
        //var link = "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/term3_iss_20130103/term3_iss_20130103HD_web.mp4";
        //saveFileFromUrlWithCommonsIO(output, link);
        //  saveFileFromUrlWithJavaIO(output, link);

    }


    // Using Java IO
    public static void saveFileFromUrlWithJavaIO(String fileName, String fileUrl)
            throws MalformedURLException, IOException {
        BufferedInputStream in = null;
        FileOutputStream fout = null;
        try {
            in = new BufferedInputStream(new URL(fileUrl).openStream());
            fout = new FileOutputStream(fileName);
            byte data[] = new byte[1024];
            int count;
            while ((count = in.read(data, 0, 1024)) != -1) {
                //clSystem.out.println("===>");
                fout.write(data, 0, count);
            }
        } finally {
            if (in != null)
                in.close();
            if (fout != null)
                fout.close();
        }
    }

    // Using Commons IO library
    // Available at http://commons.apache.org/io/download_io.cgi
    public static void saveFileFromUrlWithCommonsIO(String fileName,
                                                    String fileUrl) throws MalformedURLException, IOException {
        FileUtils.copyURLToFile(new URL(fileUrl), new File(fileName));
    }
/*
[
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/Europe-at-Night_ISS061_2020-01/Europe-at-Night_ISS061_2020-01.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/MeteorShower-Aurora_ISS061_2020-01/MeteorShower-Aurora_ISS061_2020-01.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/NightStars_ISS061_2020-01/NightStars_ISS061_2020-01.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/EastCoast-Night_ISS061_2019-12/EastCoast-Night_ISS061_2019-12.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/NorthernLights_ISS061_2019-11/NorthernLights_ISS061_2019-11.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/Nile-at-Night_ISS061_2019-11/Nile-at-Night_ISS061_2019-11.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/aurora_ISS061_2019-11/aurora_ISS061_2019-11.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/Europe-Med-atNight_ISS061_2019-11/Europe-Med-atNight_ISS061_2019-11.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/US-WestCoast_ISS061_2019-11/US-WestCoast_ISS061_2019-11.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/Nightpass-Africa-and-India_ISS061_2019-10/Nightpass-Africa-and-India_ISS061_2019-10.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/Northern_Lights_ISS060_2019-09/Northern_Lights_ISS060_2019-09-vid.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/Europe-at-night_ISS060_2019-09/Europe-at-night_ISS060_2019-09-vid.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/England-to-India-atnight_ISS060_2019-09/England-to-India-atnight_ISS060_2019-09-vid.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/CaliCoast-at-night_ISS060_2019-09/CaliCoast-at-night_ISS060_2019-09-vid.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/Europe-at-night_ISS060_2019-08/Europe-at-night_ISS060_2019-09.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/CityLights-and-Storms_ISS060_2019-09/CityLights-and-Storms_ISS060_2019-09.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/MediterraneanSea_ISS060_2019-08/MediterraneanSea_ISS060_2019-08.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/Aurora_ISS060_2019-08/Aurora_ISS060_2019-08.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/CityLights_ISS060_2019-08/CityLights_ISS060_2019-08.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/MilkyWay_ISS060_2019-08/MilkyWay_ISS060_2019-08.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/SaudiNightPass_ISS060_2019-07/SaudiNightPass_ISS060_2019-07.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/NandSALights_ISS028_2011-08/NandSALights_ISS028_2011-08.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/Cali-Quebec_ISS059_2019-06/Cali-Quebec_ISS059_2019-06.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/NightStars_ISS059_2019-05/NightStars_ISS059_2019-05.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/HurricaneOma_ISS058_2019-02/HurricaneOma_ISS058_2019-02.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/CaliCoast_ISS059_2019-05/CaliCoast_ISS059_2019-05.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/NAatnight_ISS059_2019-05/NAatnight_ISS059_2019-05.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/USatnight_ISS059_2019-05/USatnight_ISS059_2019-05.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/auroraNA_ISS057_20181112/auroraNA_ISS057_20181112.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/Stars-ISS_2018-10/Stars-ISS_2018-10.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/Canada-night_2018-09/Canada-night_2018-09.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/MoonCompilation/MoonCompilation.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/WestAfrica-Alps_2017-12/WestAfrica-Alps_2017-12.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/MiddleEast-Australia_2018-07/MiddleEast-Australia_2018-07.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/ContinentsClouds_2018-06/ContinentsClouds_2018-06.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/Indonesia_2018-06/Indonesia_2018-06.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/STS131_2010-04/STS131_2010-04.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/TransitAcrossAfrica_2017-09/TransitAcrossAfrica_2017-09.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/SouthernHemisphere_2018-04/SouthernHemisphere_2018-04.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/Oregon-YucatanPeninsula_2017-08/Oregon-YucatanPeninsula_2017-08.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/BalearicSea-LakeTurkana_2018-03/BalearicSea-LakeTurkana.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/Ireland-Egypt_ISS_2018-03/Ireland-to-Egypt_op-flow.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/aurBorealis_ISS_20170928/aurBorealis_ISS_20170928.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/auroraNA_ISS_20170928/auroraNA_ISS_20170928.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/slights_iss_20170817/slights_iss_20170817.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/milkyway_iss_20150812/milkyway_iss_20150812HD.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/borealisatlantic_iss_20120125_2/borealisatlantic_iss_20120125_2HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/moonglow_iss_20140130/moonglow_iss_20140130HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/earthcupola_iss_20120311/earthcupola_iss_20120311HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/sun_iss_20130106/sun_iss_20130106HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/term3_iss_20130103/term3_iss_20130103HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/world_iss_20121229/world_iss_20121229HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/moonset_iss_20121229/moonset_iss_20121229HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/lightningstorms_iss_20120218/lightningstorms_iss_20120218HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/uscoastaurora_iss_20120208_2/uscoastaurora_iss_20120208_2HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/pacificocean_iss_20120522/pacificocean_iss_20120522HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/jem_iss_201200623/jem_iss_201200623HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/fastcupola_iss_20120628/fastcupola_iss_20120628HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/milkywaystars_iss_20120624/milkywaystars_iss_20120624HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/solstice_iss_20120606/solstice_iss_20120606HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/tsdoksuri_iss_20120628/tsdoksuri_iss_20120628HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/circlingearth_iss_20120623/circlingearth_iss_20120623HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/solareclipse_iss_20120520/solareclipse_iss_20120520HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/stars_iss_20120512-18/stars_iss_20120512-18HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/pmc_iss_20120605/pmc_iss_20120605HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/moonpacific_iss_20120508/moonpacific_iss_20120508HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/issborealis_iss_20120423/issborealis_iss_20120423HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/overaustralis2_iss_20120425/overaustralis2_iss_20120425HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/overaustralis_iss_20120425/overaustralis_iss_20120425HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/towardaustralis_iss_20120425/towardaustralis_iss_20120425HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/indianaustralis_iss_20120428/indianaustralis_iss_20120428HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/issaustralis_iss_20120428/issaustralis_iss_20120428HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/risingmoon_iss_20120506/risingmoon_iss_20120506HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/indiancupola_iss_20120331/indiancupola_iss_20120331HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/enamerica_iss_20120413/enamerica_iss_20120413HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/weuropesudan_iss_20120413/weuropesudan_iss_20120413HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/namerica_iss_20120422/namerica_iss_20120422HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/indianaustralis_iss_20120424/indianaustralis_iss_20120424HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/europecitylights_iss_20120414/europecitylights_iss_20120414HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/auroraus_iss_20120413/auroraus_iss_20120413HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/eastus_iss_20120412/eastus_iss_20120412HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/europeaurora_iss_20120405/europeaurora_iss_20120405HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/auroralights_iss_20120328/auroralights_iss_20120328HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/caliaurora_iss_20120328/caliaurora_iss_20120328HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/sunsetchina_iss_20120413/sunsetchina_iss_20120413HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/europelights_iss_20120402/europelights_iss_20120402HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/namericaaurora_iss_20120328/namericaaurora_iss_20120328HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/starsbehind_iss_20120317/starsbehind_iss_20120317HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/worldstars_iss_20120310/worldstars_iss_20120310HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/towardsaustralis_iss_20120310/towardsaustralis_iss_20120310HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/australis_iss_20120304/australis_iss_20120304HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/australis_iss_20120310/australis_iss_20120310HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/earthcupola_iss_20120312/earthcupola_iss_20120312HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/kenyaaustralisl_iss_20120303/kenyaaustralisl_iss_20120303HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/greatlakesaurora_iss_20120125/greatlakesaurora_iss_20120125HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/canadacentralusaurora_iss_20120207/canadacentralusaurora_iss_20120207HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/westusaurora_iss_20120131/westusaurora_iss_20120131HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/uscoastaurora_iss_20120208/uscoastaurora_iss_20120208HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/usatlanticaurora_iss_20120203/usatlanticaurora_iss_20120203HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/pacificaurora_iss_20120122/pacificaurora_iss_20120122HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/StarsCupola_iss_20120226/StarsCupola_iss_20120226HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/borealiscan_iss_20120204/borealiscan_iss_20120204HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/borealisatlantic_iss_20120201/borealisatlantic_iss_20120201HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/milkywaystarsbor_iss_20120123/milkywaystarsbor_iss_20120123HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/borealisatlantic_iss_20120202/borealisatlantic_iss_20120202HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/borealiscanada_iss_20120202/borealiscanada_iss_20120202HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/borealis_iss_20120125/borealis_iss_20120125HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/eunitedstates_iss_20120129/eunitedstates_iss_20120129HD.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/swcanada_iss_20120125/swcanada_iss_20120125HD.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/mexbrunswick_iss_20120130/mexbrunswick_iss_20120130HD.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/dakotaquebec_iss_20120126/dakotaquebec_iss_20120126HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/centplains_iss_20120130/centplains_iss_20120130HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/borealispacific_iss_20120125/borealispacific_iss_20120125HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/borealiscan_iss_20120129/borealiscan_iss_20120129HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/moonset_iss_20120109/moonset_iss_20120109HD.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/australis_iss_20120103/australis_iss_20120103HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/milkyway_iss_20111229/milkyway_iss_20111229HD.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/LovejoyMontage/LovejoyMontageHD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/weuropestars_iss_20111222/weuropestars_iss_20111222HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/borealis_iss_20111221/borealis_iss_20111221HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/lovejoy_iss_20111221/lovejoy_iss_20111221HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/borealisatlantic_iss_20111211/borealisatlantic_iss_20111211HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/acrossUS_iss_20111015/acrossUS_iss_20111015HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/stars_iss_20111022/stars_iss_20111022HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/progress2_iss_20111029/progress2_iss_20111029HD.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/weurope_iss_20111015/weurope_iss_20111015HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/USnight_iss_20111018/USnight_iss_20111018HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/USnight_iss_20111016/USnight_iss_20111016HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/africa_iss_20111006/africa_iss_20111006HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/aurora_iss_20110929/aurora_iss_20110929HD.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/aurora_iss_20110918/aurora_iss_20110918HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/aurora_iss_20110917/aurora_iss_20110917HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/aurora_iss_20110911/aurora_iss_20110911HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/irene_iss_20110826/irene_iss_20110826HD_web.mp4",
    "https://eol.jsc.nasa.gov/BeyondThePhotography/CrewEarthObservationsVideos/videos/aurora_iss_20110907/aurora_iss_20110907HD_web.mp4"
]
 */
}
