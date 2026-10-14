# Compressione di file testuali in Java

Questo progetto contiene un programma da riga di comando per applicare a un
file di testo diverse codifiche e trasformazioni usate nella compressione, e
per invertirle con il programma di decompressione. Le implementazioni lavorano
su stringhe di caratteri: non sono pensate per comprimere file binari.

## Requisiti

È necessario avere installato il JDK 21 o una versione successiva.

## Compilazione

Apri un terminale nella cartella del progetto e compila i sorgenti:

```bash
javac *.java
```

## Utilizzo

I comandi accettano il percorso del file seguito da una o più opzioni di
codifica. Per esempio, dopo aver creato una copia del file da comprimere:

```bash
java Comprimi copia.txt -lz77
```

Per decomprimere il file con la stessa codifica:

```bash
java Decomprimi copia.txt -lz77
```

**Attenzione:** entrambi i programmi sovrascrivono il file indicato. Conserva
una copia dell'originale prima di eseguirli. Se applichi più codifiche,
`Comprimi` le esegue nell'ordine indicato; `Decomprimi` deve ricevere le stesse
opzioni nell'ordine inverso. Per esempio:

```bash
java Comprimi copia.txt -bwt -mtf -huffman
java Decomprimi copia.txt -huffman -mtf -bwt
```

## Codifiche disponibili

- **Burrows-Wheeler (`-bwt`)**: riordina i caratteri in blocchi per portare
  caratteri simili vicini e rendere il testo più adatto alle codifiche
  successive.
- **Move-to-Front (`-mtf`)**: sostituisce ogni carattere con la sua posizione
  in una lista aggiornata spostando quel carattere in testa.
- **Run-Length Encoding (`-rle`)**: rappresenta ogni sequenza di caratteri
  uguali con il carattere e la sua lunghezza.
- **Huffman (`-huffman`)**: assegna codici binari più brevi ai caratteri più
  frequenti e più lunghi a quelli meno frequenti.
- **LZ77 (`-lz77`)**: sostituisce le sequenze già incontrate con una tripla
  contenente distanza, lunghezza della corrispondenza e carattere successivo.
- **LZ78 (`-lz78`)Fuori Uso**: costruisce un dizionario progressivo e rappresenta le
  sequenze tramite l'indice di una voce precedente e il carattere successivo.
