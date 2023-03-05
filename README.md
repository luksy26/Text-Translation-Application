# Text-Translation-Application

Avem 3 clase principale, care se ocupa cu stocarea datelor, si anume:
  - Definition: retine date despre una dintre definitiile unui cuvant;
  - Word: retine date despre unul dintre cuvintele dintr-un dictionar, acesta contine o lista de definitii;
  - Dictionary: retine date despre un dictionar, care contine o lista de cuvinte.

Ne mai folosim de clasa Administration, unde implementam metode care comunica cu informatiile din dictionare.

Toate campurile acestor clase au modificator de acces private, cu implementarea de metode get si set.

Clasele Definition si Word implementeaza Comparable, in scopul de a sorta cu usurinta liste cu elemente de acest tip.

Clasa Word implementeaza 2 metode:
  - addDefinition: adauga o definitie cuvantului, la sfarsitul listei de definitii;
  - deleteDefinitionAtPosition: sterge definitia de la o pozitie data.
  
Clasa Dictionary implementeaza 2 metode:
  - addWord: adauga un cuvant dictionarului, la sfarsitul listei de cuvinte;
  - deleteWordAtPosition: sterge cuvantul de la o pozitie data.
 
Clasa Administration contine urmatoarele:
  - o lista de dictionare
  - o variabila ce reprezinta numarul de dictionare
  - si metodele de implementat pentru task-uri.
 
 In plus, pentru usurinta, am implementat metoda getSynonyms, care returneaza o lista de sinonime pentru un cuvant primindu-l pe acesta si limba din care provine.
 Sinonimele vor fi extrase doar din campul text al definitiei din dictionarul de sinonime pentru fiecare cuvant, in cazul in care acesta exista. In caz contrar,
 returnam null.
 
 Pentru metoda translateSentences am folosit 3 stringBuildere pentru a stoca eventualele variante de traducere. Algoritmul functioneaza dupa cum urmeaza: 
  - initial numarul de moduri de traducere este unul singur, folosind doar traducerile directe, fara sinonime;
  - se traduc cuvintele pe rand in limba ceruta folosind metoda de la task6 si se extrag sinonimele pentru aceasta traducere, limba fiind cunoscuta;
  - daca lista de sinonime este goala, nu facem decat sa actualizam stringBuilderele si sa trecem la urmatorul cuvant;
  - daca avem un sinonim in lista, inseamna ca ne dublam numarul de moduri al traducerii (regula produsului);
  - in s[0] pastram traducerea default, in s[1] facem prima variatie cu sinonimul gasit, iar in s[2] vom face variatia numai daca este prima oara cand gasim un sinonim unic;
  - problema putea fi abordata si invers, facem variatia in cazul opus, deci daca e a doua oara cand gasim sinonim unic, dar pentru cerinta curenta este suficient;
  - daca se intra de 2 ori pe acest caz (cu sinonim unic) deja sunt 4 potentiale moduri de traducere, iar noi am retinut 3, de acum nu mai trebuie sa facem variatii;
  - daca avem 2 sau mai multe sinonime, deja numarul de moduri de traducerea va fi minim triplat, deci am terminat problema, nici nu ne intereseaza daca l-am dublat inainte
  cu un sinonim unic, vom pune in fiecare stringBuilder o varianta de traducere si de aici incolo nu mai facem variatii, deci nu ne mai intereseaza sinonime;
  - la epuizarea cuvintelor verificam numarul de moduri obtinut (poate fi doar 1,2,3,4,6)
  - daca e >= 1 adaugam primul string, daca e >=2 il adaugam si pe al doilea, daca e >=3 il adaugam si pe al treilea intr-un ArrayList, pe care il returnam
  
 Pentru exportDictionary:
  - sortam definitiile fiecarui cuvant crescator dupa anul dictionarului, apoi sortam alfabetic cuvintele;
  - punem aceste date intr-un dictionar si il parsam la Json folosind Gson
  - cream un fisier nou cu un titlu adecvat limbii dictionarului si scriem in el datele despre dictionar obtinute
  
In Main:
  - iteram prin toate fisierele din directorul "input", punem la inceputul string-ului ce va avea continutul fiecarui fisier { "words": , pentru a reprezenta
  structura unui obiect dictionar in format Json, si il deserializam folosing Gson;
  - cream o instanta a clasei Administration si vom face testa metodele implementate cu 2-3 teste pentru fiecare task.
  
  Obs: am observat ca la serializarea din nou a datelor caracterul apostrof -> ' <- este scris ca si \u0027, care este chiar unicode-ul caracterului. Cred ca e o chestie normala, avand in vedere ca apostroful e un caracter destul de important in programare si trebuie sa aiba un scop clar (in text si in sintaxa de cod). Am observat ca in memorie
ramane ca si caracter, dar la serializare se transforma in unicode.
