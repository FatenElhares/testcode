package com.pixalione.monetoring.data_preprocessing.Core;

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>Titre : Paice Husk Stemmer</p>
 * <p>Traduit de la version PHP de Alexis Ulrich (ulrich_alexis@yahoo.com) par Tito Colonna (tcolonna@ecoco.fr)</p>
 * <p>Cette version ne dИrive que les mots simples sans les comparer Ю une liste de stopwords.</p>
 *
 * <p>Title: Paice Husk Stemmer</p>
 * <p>Translated by Tito Colonna (tcolonna@ecoco.fr) from the PHP version by Alexis Ulrich (ulrich_alexis@yahoo.com)</p>
 * <p>That version does not handle compound words and does not use a stopwords list.</p>
 *
 * @version 1.0
 */
public class PaiceHuskStemmer {
    static Vector punctuation = null;
    static Vector stopwords = null;
    static String rule_pattern = "^([a-zЮБХИЙКНОТШЫГ]*)(\\*){0,1}(\\d)([a-zЮБХИЙКНОТШЫГ]*)([.|>])";
    static String punctuation_array[] = {".", ",", ";", ":", "!", "?", "'", "`", "\"", "(", ")", "--"};
    static String stemrules_array[] = {
            "esre1>",			// { -erse > -ers }
            "esio1>",			// { -oise > -ois }
            "siol1.",			// { -lois > -loi }
            "siof0.",			// { -fois > -fois }
            "sioe0.",			// { -eois > -eois }
            "sio3>",			// { -ois > - }
            "st1>",				// { -ts > -t }
            "sf1>",				// { -fs > -f }
            "sle1>",			// { -els > -el }
            "slo1>",			// { -ols > -ol }
            "sИ1>",				// { -Иs > -И }
            "Иtuae5.",			// { -eautИ > - }
            "Иtuae2.",			// { -eautИ > -eau }
            "tnia0.",			// { -aint > -aint }
            "tniv1.",			// { -vint > -vin }
            "tni3>",			// { -int > - }
            "suor1.",			// { -rous > -ou }
            "suo0.",			// { -ous > -ous }
            "sdrail5.",			// { -liards > -l }
            "sdrai4.",			// { -iards > -i }
            "erХi1>",			// { -iХre > -ier }
            "sesue3x>",			// { -euses > -euse }
            "esuey5i.",			// { -yeuse > -i }
            "esue2x>",			// { -euse > -eux }
            "se1>",				// { -es > -e }
            "erХg3.",			// { -gХre > -g }
            "eca1>",			// { -ace > -ac }
            "esiah0.",			// { -haise > - }
            "esi1>",			// { -ise > -is }
            "siss2.",			// { -ssis > -ss }
            "sir2>",			// { -ris > -r }
            "sit2>",			// { -tis > -t }
            "eganИ1.",			// { -Иnage > -Иnag }
            "egalli6>",			// { -illage > - }
            "egass1.",			// { -ssage > -sag }
            "egas0.",			// { -sage > - }
            "egat3.",			// { -tage > - }
            "ega3>",			// { -age > - }
            "ette4>",			// { -ette > - }
            "ett2>",			// { -tte > -t }
            "etio1.",			// { -oite > -oit }
            "tioГ4c.",			// { -Гoit > -c }
            "tio0.",			// { -oit > -oit }
            "et1>",				// { -te > -t }
            "eb1>",				// { -be > -b }
            "snia1>",			// { -ains > -ain }
            "eniatnau8>",		// { -uantaine > - }
            "eniatn4.",			// { -ntaine > -nt }
            "enia1>",			// { -aine > -ain }
            "niatnio3.",		// { -ointain > -oint }
            "niatg3.",			// { -gtain > -gt }
            "eИ1>",				// { -Иe > -И }
            "Иhcat1.",			// { -tachИ > -tach }
            "Иhca4.",			// { -achИ > - }
            "Иtila5>",			// { -alitИ > - }
            "Иtici5.",			// { -icitИ > - }
            "Иtir1.",			// { -ritИ > -rit }
            "Иti3>",			// { -itИ > - }
            "Иgan1.",			// { -nagИ > -nag }
            "Иga3>",			// { -agИ > - }
            "Иtehc1.",			// { -chetИ > -chet }
            "Иte3>",			// { -etИ > - }
            "Иit0.",			// { -tiИ > -tiИ }
            "И1>",				// { -И > - }
            "eire4.",			// { -erie > - }
            "eirue5.",			// { -eurie > - }
            "eio1.",			// { -oie > -oi }
            "eia1.",			// { -aie > -ai }
            "ei1>",				// { -ie > -i }
            "eng1.",			// { -gne > -gn }
            "xuaessi7.",		// { -isseaux > - }
            "xuae1>",			// { -eaux > -eau }
            "uaes0.",			// { -seau > -seau }
            "uae3.",			// { -eau > - }
            "xuave2l.",			// { -evaux > -eval }
            "xuav2li>",			// { -vaux > -vail }
            "xua3la>",			// { -aux > -al }
            "ela1>",			// { -ale > -al }
            "lart2.",			// { -tral > -tr }
            "lani2>",			// { -inal > -in }
            "laИ2>",			// { -Иal > -И }
            "siay4i.",			// { -yais > -i }
            "siassia7.",		// { -aissais > - }
            "siarv1*.",			// { -vrais > -vrai if intact }
            "sia1>",			// { -ais > -ai }
            "tneiayo6i.",		// { -oyaient > -oi }
            "tneiay6i.",		// { -yaient > -i }
            "tneiassia9.",		// { -aissaient > - }
            "tneiareio7.",		// { -oieraient > -oi }
            "tneia5>",			// { -aient > - }
            "tneia4>",			// { -aient > -a }
            "tiario4.",			// { -oirait > -oi }
            "tiarim3.",			// { -mirait > -mir }
            "tiaria3.",			// { -airait > -air }
            "tiaris3.",			// { -sirait > -sir }
            "tiari5.",			// { -irait > - }
            "tiarve6>",			// { -evrait > - }
            "tiare5>",			// { -erait > - }
            "iare4>",			// { -erai > - }
            "are3>",			// { -era > - }
            "tiay4i.",			// { -yait > -i }
            "tia3>",			// { -ait > - }
            "tnay4i.",			// { -yant > -i }
            "emХiu5>",			// { -uiХme > - }
            "emХi4>",			// { -iХme > - }
            "tnaun3.",			// { -nuant > -nu }
            "tnauqo3.",			// { -oquant > -oqu }
            "tnau4>",			// { -uant > - }
            "tnaf0.",			// { -fant > -fant }
            "tnatИ2>",			// { -Иtant > -Иt }
            "tna3>",			// { -ant > - }
            "tno3>",			// { -ont > - }
            "zeiy4i.",			// { -yiez > -i }
            "zey3i.",			// { -yez > -i }
            "zeire5>",			// { -eriez > - }
            "zeird4.",			// { -driez > -d }
            "zeirio4.",			// { -oiriez > -oi }
            "ze2>",				// { -ez > - }
            "ssiab0.",			// { -baiss > - }
            "ssia4.",			// { -aiss > - }
            "ssi3.",			// { -iss > - }
            "tnemma6>",			// { -amment > - }
            "tnemesuey9i.",		// { -yeusement > -i }
            "tnemesue8>",		// { -eusement > - }
            "tnemevi7.",		// { -ivement > - }
            "tnemessia5.",		// { -aissement > -aiss }
            "tnemessi8.",		// { -issement > - }
            "tneme5>",			// { -ement > - }
            "tnemia4.",			// { -aiment > -ai }
            "tnemИ5>",			// { -Иment > - }
            "el2l>",			// { -le > -l }
            "lle3le>",			// { -ell > -el }
            "letТ0.",			// { -Тtel > -Тtel }
            "lepp0.",			// { -ppel > -ppel }
            "le2>",				// { -el > - }
            "srei1>",			// { -iers > -ier }
            "reit3.",			// { -tier > -t }
            "reila2.",			// { -alier > -ali }
            "rei3>",			// { -ier > - }
            "ertБe5.",			// { -eБtre > - }
            "ertБИ1.",			// { -ИБtre > -ИБtr }
            "ertБ4.",			// { -Бtre > - }
            "drai4.",			// { -iard > - }
            "erdro0.",			// { -ordre > -ordre }
            "erute5.",			// { -eture > - }
            "ruta0.",			// { -atur > -atur }
            "eruta1.",			// { -ature > -atur }
            "erutiov1.",		// { -voiture > -voitur }
            "erub3.",			// { -bure > -b }
            "eruh3.",			// { -hure > -h }
            "erul3.",			// { -lure > -l }
            "er2r>",			// { -re > -r }
            "nn1>",				// { -nn > -n }
            "rХi3.",			// { -iХr > - }
            "srev0.",			// { -vers > -vers }
            "sr1>",				// { -rs > -r }
            "rid2>",			// { -dir > -d }
            "re2>",				// { -er > - }
            "xuei4.",			// { -ieux > - }
            "esuei5.",			// { -ieuse > - }
            "lbati3.",			// { -itabl > -it }
            "lba3>",			// { -abl > - }
            "rueis0.",			// { -sieur > - }
            "ruehcn4.",			// { -ncheur > -nc }
            "ecirta6.",			// { -atrice > - }
            "ruetai6.",			// { -iateur > - }
            "rueta5.",			// { -ateur > - }
            "rueir0.",			// { -rieur > - }
            "rue3>",			// { -eur > - }
            "esseti6.",			// { -itesse > - }
            "essere6>",			// { -eresse > - }
            "esserd1.",			// { -dresse > -dress }
            "esse4>",			// { -esse > - }
            "essiab1.",			// { -baisse > -baiss }
            "essia5.",			// { -aisse > - }
            "essio1.",			// { -oisse > -oiss }
            "essi4.",			// { -isse > - }
            "essal4.",			// { -lasse > -l }
            "essa1>",			// { -asse > -ass }
            "ssab1.",			// { -bass > -bas }
            "essurp1.",			// { -prusse > -uss }
            "essu4.",			// { -usse > - }
            "essi1.",			// { -isse > -ss }
            "ssor1.",			// { -ross > -ros }
            "essor2.",			// { -rosse > -ros }
            "esso1>",			// { -osse > -oss }
            "ess2>",			// { -sse > -s }
            "tio3.",			// { -oit > - }
            "rХs2re.",			// { -sХr > -ser }
            "rХ0e.",			// { -Хr > -Хre }
            "esn1.",			// { -nse > -Хns }
            "eu1>",				// { -ue > -u }
            "sua0.",			// { -aus > -aus }
            "su1>",				// { -us > -u }
            "utt1>",			// { -utt > -tt }
            "tuГ3c.",			// { -Гut > -c }
            "uГ2c.",			// { -Гu > -c }
            "ur1.",				// { -ru > -r }
            "ehcn2>",			// { -nche > -nc }
            "ehcu1>",			// { -uche > -uch }
            "snorr3.",			// { -rrons > -rr }
            "snoru3.",			// { -urons > -ur }
            "snorua3.",			// { -aurons > -aur }
            "snorv3.",			// { -vrons > -vr }
            "snorio4.",			// { -oirons > -oi }
            "snori5.",			// { -irons > - }
            "snore5>",			// { -erons > - }
            "snortt4>",			// { -ttrons > -tt }
            "snortНa7.",		// { -aНtrons > - }
            "snort3.",			// { -trons > -tr }
            "snor4.",			// { -rons > - }
            "snossi6.",			// { -issons > - }
            "snoire6.",			// { -erions > - }
            "snoird5.",			// { -drions > -d }
            "snoitai7.",		// { -iations > - }
            "snoita6.",			// { -ations > - }
            "snoits1>",			// { -stions > -stion }
            "noits0.",			// { -stion > -stion }
            "snoi4>",			// { -ions > - }
            "noitaci7>",		// { -ication > - }
            "noitai6.",			// { -iation > - }
            "noita5.",			// { -ation > - }
            "noitu4.",			// { -ution > -u }
            "noi3>",			// { -ion > - }
            "snoya0.",			// { -ayons > -ayons }
            "snoy4i.",			// { -yons > -i }
            "snoГa1.",			// { -aГons > -aГon }
            "snoГr1.",			// { -rГons > -rГon }
            "snoe4.",			// { -eons > - }
            "snosiar1>",		// { -raisons > - }
            "snola1.",			// { -alons > -alon }
            "sno3>",			// { -ons > - }
            "sno1>",			// { -ons > -on }
            "noll2.",			// { -llon > -ll }
            "tnennei4.",		// { -iennent > -ien }
            "ennei2>",			// { -ienne > -ien }
            "snei1>",			// { -iens > -ien }
            "sneИ1>",			// { -Иens > -Иen }
            "enneИ5e.",			// { -Иenne > -e }
            "neИ3e.",			// { -Иen > -e }
            "neic0.",			// { -cien > -cien }
            "neiv0.",			// { -vien > -vien }
            "nei3.",			// { -ien > - }
            "sc1.",				// { -cs > -c }
            "sd1.",				// { -ds > -d }
            "sg1.",				// { -gs > -g }
            "sni1.",			// { -ins > -in }
            "tiu0.",			// { -uit > - }
            "ti2.",				// { -it > - }
            "sp1>",				// { -ps > -p }
            "sna1>",			// { -ans > -an }
            "sue1.",			// { -eus > -eu }
            "enn2>",			// { -nne > -n }
            "nong2.",			// { -gnon > -gn }
            "noss2.",			// { -sson > -ss }
            "rioe4.",			// { -eoir > - }
            "riot0.",			// { -toir > -toir }
            "riorc1.",			// { -croir > -croi }
            "riovec5.",			// { -cevoir > -c }
            "rio3.",			// { -oir > - }
            "ric2.",			// { -cir > -l }
            "ril2.",			// { -lir > -l }
            "tnerim3.",			// { -mirent > -mir }
            "tneris3>",			// { -sirent > -sir }
            "tneri5.",			// { -irent > - }
            "tНa3.",			// { -aНt > - }
            "riss2.",			// { -ssir > -ss }
            "tН2.",				// { -Нt > - }
            "tБ2>",				// { -Бt > - }
            "ario2.",			// { -oira > -oi }
            "arim1.",			// { -mira > -m }
            "ara1.",			// { -ara > -ar }
            "aris1.",			// { -sira > -sir }
            "ari3.",			// { -ira > - }
            "art1>",			// { -tra > -tr }
            "ardn2.",			// { -ndra > -nd }
            "arr1.",			// { -rra > -rr }
            "arua1.",			// { -aura > -aur }
            "aro1.",			// { -ora > -or }
            "arv1.",			// { -vra > -vr }
            "aru1.",			// { -ura > -ur }
            "ar2.",				// { -ra > - }
            "rd1.",				// { -dr > -d }
            "ud1.",				// { -du > - }
            "ul1.",				// { -lu > -l }
            "ini1.",			// { -ini > -in }
            "rin2.",			// { -nir > - }
            "tnessiab3.",		// { -baissent > -baiss }
            "tnessia7.",		// { -aissent > - }
            "tnessi6.",			// { -issent > - }
            "tnessni4.",		// { -inssent > -ins }
            "sini2.",			// { -inis > -in }
            "sl1.",				// { -ls > -l }
            "iard3.",			// { -drai > -d }
            "iario3.",			// { -oirai > -oi }
            "ia2>",				// { -ai > - }
            "io0.",				// { -oi > -oi }
            "iule2.",			// { -elui > -el }
            "i1>",				// { -i > - }
            "sid2.",			// { -dis > -d }
            "sic2.",			// { -cis > -c }
            "esoi4.",			// { -iose > - }
            "ed1.",				// { -de > -d }
            "ai2>",				// { -ia > - }
            "a1>",				// { -a > - }
            "adr1.",			// { -rda > -rd }
            "tnerХ5>",			// { -Хrent > - }
            "evir1.",			// { -rive > -riv }
            "evio4>",			// { -oive > - }
            "evi3.",			// { -ive > - }
            "fita4.",			// { -atif > - }
            "fi2>",				// { -if > - }
            "enie1.",			// { -eine > -ein }
            "sare4>",			// { -eras > - }
            "sari4>",			// { -iras > - }
            "sard3.",			// { -dras > -d }
            "sart2>",			// { -tras > -tr }
            "sa2.",				// { -as > - }
            "tnessa6>",			// { -assent > - }
            "tnessu6>",			// { -ussent > - }
            "tnegna3.",			// { -angent > -ang }
            "tnegi3.",			// { -igent > -ig }
            "tneg0.",			// { -gent > -gent }
            "tneru5>",			// { -urent > - }
            "tnemg0.",			// { -gment > -gment }
            "tnerni4.",			// { -inrent > -in }
            "tneiv1.",			// { -vient > -vien }
            "tne3>",			// { -ent > - }
            "une1.",			// { -enu > -en }
            "en1>",				// { -ne > -n }
            "nitn2.",			// { -ntin > - }
            "ecnay5i.",			// { -yance > -i }
            "ecnal1.",			// { -lance > -lanc }
            "ecna4.",			// { -ance > - }
            "ec1>",				// { -ce > -c }
            "nn1.",				// { -nn > -n }
            "rit2>",			// { -tir > - }
            "rut2>",			// { -tur > -t }
            "rud2.",			// { -dur > -d }
            "ugn1>",			// { -ngu > -ng }
            "eg1>",				// { -ge > -g }
            "tuo0.",			// { -out > -out }
            "tul2>",			// { -lut > -l }
            "tШ2>",				// { -Шt > - }
            "ev1>",				// { -ve > -v }
            "vХ2ve>",			// { -Хv > -ev }
            "rtt1>",			// { -ttr > -tt }
            "emissi6.",			// { -issime > - }
            "em1.",				// { -me > -m }
            "ehc1.",			// { -che > -ch }
            "cИi2cХ.",			// { -iИc > -iХc }
            "libi2l.",			// { -ibil > -ibl }
            "llie1.",			// { -eill > -eil }
            "liei4i.",			// { -ieil > -i }
            "xuev1.",			// { -veux > -veu }
            "xuey4i.",			// { -yeux > -i }
            "xueni5>",			// { -ineux > - }
            "xuell4.",			// { -lleux > -l }
            "xuere5.",			// { -ereux > - }
            "xue3>",			// { -eux > - }
            "rbИ3rbХ.",			// { -Иbr > -Хbr }
            "tur2.",			// { -rut > -r }
            "rirИ4re.",			// { -Иrir > -er }
            "rir2.",			// { -rir > -r }
            "cБ2ca.",			// { -Бc > -ac }
            "snu1.",			// { -uns > -un }
            "rtНa4.",			// { -aНtr > - }
            "long2.",			// { -gnol > -gn }
            "vec2.",			// { -cev > -c }
            "Г1c>",				// { -Г > -c }
            "ssilp3.",			// { -pliss > -pl }
            "silp2.",			// { -plis > -pl }
            "tХhc2te.",			// { -chХt > -chet }
            "nХm2ne.",			// { -mХn > -men }
            "llepp1.",			// { -ppell > -ppel }
            "tan2.",			// { -nat > -n }
            "rvХ3rve.",			// { -Хvr > -evr }
            "rvИ3rve.",			// { -Иvr > -evr }
            "rХ2re.",			// { -Хr > -er }
            "rИ2re.",			// { -Иr > -er }
            "tХ2te.",			// { -Хt > -et }
            "tИ2te.",			// { -Иt > -et }
            "epp1.",			// { -ppe > -pp }
            "eya2i.",			// { -aye > -ai }
            "ya1i.",			// { -ay > -ai }
            "yo1i.",			// { -oy > -oi }
            "esu1.",			// { -use > -us }
            "ugi1.",			// { -igu > -g }
            "tt1.",				// { -tt > -t }
            "end0."
    };
    static int rulesCount = stemrules_array.length;

    public PaiceHuskStemmer() {
    }

    public static String stemPaiceHusk(String form) {
        boolean stem_found = false;
        String reversed = reverse(form);
        String reversed_stem = "";
        int rule_number = 0;
        String rule;
        while (true) {
            rule_number = getFirstRule(reversed, rule_number);
            if (rule_number == -1) {
                break;
            }
            rule = stemrules_array[rule_number];
            Pattern pattern = Pattern.compile(rule_pattern);
            Matcher matcher = pattern.matcher(rule);
            if (matcher.matches()) {
                if (matcher.group(2) == null || !(matcher.group(2)).equals("*")) {
                    reversed_stem = matcher.group(4) + reversed.substring(Integer.parseInt(matcher.group(3)));
                    if (checkAcceptability(reversed_stem)) {
                        reversed = reversed_stem;
                        if (matcher.group(5).equals(".")) break;
                    } else {
                        rule_number++;
                    }
                } else {
                    rule_number++;
                }
            } else {
                rule_number++;
            }
        }
        return reverse(reversed);
    }

    private static int getFirstRule(String reversed, int startAt) {
        String new_rule = "";
        String rule = "";
        for (int count = startAt; count < rulesCount; count++) {
            rule = stemrules_array[count];
            new_rule = regexpMatchAndReplace(rule, rule_pattern, "$1");
            if (reversed.startsWith(new_rule)) return count;
        }
        return -1;
    }

    private static String regexpMatchAndReplace(String text, String patternStr, String replaceStr) {
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(text);
        return matcher.replaceAll(replaceStr);
    }

    private static boolean regexpFind(String text, String patternStr) {
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(text);
        return matcher.find();
    }

    private static boolean checkAcceptability(String reversed) {
        if (regexpFind(reversed, "[aЮБeХИЙКiНОoТuШЫy]$")) {
            // if form starts with vowel, at least two letters must remain
            return (reversed.length() > 2);
        } else {
            // if form starts with a consonant, at least two letters must remain
            if (reversed.length() <= 2) return false;
            // and at least one of these must be a vowel or y
            return regexpFind(reversed, "[aЮБeХИЙКiНОoТuШЫy]");
        }
    }

    private static String reverse(String source) {
        int i, len = source.length();
        StringBuffer dest = new StringBuffer(len);

        for (i = (len - 1); i >= 0; i--)
            dest.append(source.charAt(i));
        return dest.toString();
    }

}