# 🧩 Compressione di File in Java

Un progetto Java che implementa un insieme di algoritmi di compressione e decompressione, combinando più tecniche per ottenere file più leggeri e facilmente gestibili.

---

## 🚀 Utilizzo

### 🔧 Compilazione
Compila tutti i file Java presenti nella directory:
```bash
javac *.java
```
Per comprimere:
```bash
java Comprimi <percorso_file>
```
Per decomprimere:
```bash
java Decomprimi <percorso_file>
```

# Algoritmi di compressione implememntati
Burrows-Wheeler Trasformation: riordina i caratteri in maniera più compressibile

Move to front: codifica con il numero di indice della lista, e lo muove a zero

Run lenght encoding: carattere e ripetizioni di fila

Codice di Huffman: crea un albero binario unendo i due nodi con probabilità minore

LZ77: insieme di triple che codificano stringhe già viste

LZ78: insieme di tuple che codificano indice prefisso parola nel dizionario e il prossimo carattere
