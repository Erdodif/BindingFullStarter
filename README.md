# Binding osztályok

Ez a repó a Binding osztályok gyakorló projektje.

# ViewBinding

A megírt *xml* resource-ból minden azonosítóval rendelkező elemhez egy **null-safe** hivatkozást csatol, amiket egy `activity` vagy `fragment` specifikus `binding` osztályba rendezi.

A binding az activity nevének és a binding szónak a camel-case formályából áll.

* Például az `activity_main.xml`-hez az `ActivityMainBinding` osztályt fogja rendelni.

Az *xml* frissítésével automatikusan újra generálódik, az egyszeri beállítás után nincs vele teendőnk.

Ez mit is jelent?

* Létrehozunk egy elemet
* Adunk neki egy azonosítót
* És máris elérhető a binding osztályban
  * nem kell iniciálni,
  * a típusa megegyezik az elem típusával.
* Később, ha megváltoztatjuk az elem típusát (pl egyszerű gombról áttérünk a google új `Materialbutton` osztályára) a binding azt is követi, és mentés után annak minden funkciója elérhető.
  * Nyilván ha textview-ból állunk átt button-re, akkor a metódusok hivatkozása lehet gond, de erre számíthatunk.

# DataBinding

Az *xml* forráshoz saját típusú változót rendelhetünk, és annak a paramétereit tudod az adott elemekhez rendelni. A generált binding változóihoz getter és setter lessz kapcsolva, amivel az összes elem tartalmát lehet automatikusan átírni.

> Mondjuk a majdani Film példával az egyik textView-hoz a film címét rendeljük, a másikhoz a kategóriát, és amikor a binding setFilm() metódusát hívjuk meg, az abban deffiniált film paramétereit leosztja az annak megfelelő textView-kba.

A DataBinding magába foglalja a ViewBinding osztályt, így elég csak az egyiket beállítani a gradle fájlban.

## Importálás

Az importáláshoz az applikációhoz tartozó gradle fájlba kell az alábbi kiegészítést mellékelni. (Module: BindingFul.app)

###### ViewBinding

> ```gradle
> android{
>  …
>     buildFeatures{
>         viewBinding true
>     }
>  …
> }
> ```

###### DataBinding

> ```gradle
> android{
>  …
>     buildFeatures{
>         dataBinding true
>     }
>  …
> }
> ```
