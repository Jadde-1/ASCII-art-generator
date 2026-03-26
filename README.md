# ASCII Art Generator

Et Java-baseret værktøj til at konvertere billeder til ASCII-kunst med en minimalistisk GUI flere konverteringsmuligheder.

---

## Indhold

- [Om projektet](#om-projektet)
- [Funktioner](#funktioner)
- [Planlagte funktioner](#planlagte-funktioner)
- [Installation](#installation)
- [Brug](#brug)
- [Bidrag](#bidrag)

---

## Om projektet

ASCII Art Generator konverterer billeder og måske mapper med billeder til ASCII-kunst via en simpel grafisk brugergrænseflade. Projektet fokuserer på ydeevne, brugervenlighed og eksportmuligheder.

---

## Funktioner

### Inputhaandtering
- Indsæt billedet via GUI (drag-and-drop eller filvalg)
- Massekovertering af hele mapper med billeder
- Understøttelse af flere billedformater (PNG, JPG, BMP, WEBP)

### Konvertering
- Valg mellem sort/hvid og farve-output
- Valgfri tegnssaet til konvertering:
  - Fuld ASCII (alle printbare tegn)
  - Bogstaver og tal
  - Konamitegn og specialsymboler
  - Brugerdefineret tegnssaet

### Ydeevne
- Multithreading til parallel behandling af billeder
- CUDA/GPU-acceleration til store billeder og batchkovertering

### Statistik og analyse
- Tael for brugte symboler med frekvensfordeling
- Konverteringstimer og ydeevnemaaling
- Database med gemte konverteringsparametre og beskrivelser af tegnssaet

### Eksport
- Eksport til PNG, PDF og ren tekstfil
- Valg af output-oplosning og skriftstorrelse

### Graenseflade
- Minimalistisk GUI med live preview
- Animerede overgange ved konvertering
- Installationsprogram til Windows, macOS og Linux

---

## Planlagte funktioner

### Konvertering og kvalitet
- Kantdetektering (Canny/Sobel) for skarpere ASCII-output
- Tilpasning af kontrast og lysstyrke for konvertering
- Inverteret farvetilstand (lys tekst pa mork baggrund)
- Tilpasselig outputbredde i tegn (kolonne-begransning)
- Bevarelse af billedformat (aspect ratio-korrektion)

### Tegnssaet og stil
- Forhaaandsindlaeste tegnssaet-profiler (f.eks. "blod", "geometrisk", "minimal")
- Tegnssaetseditor til at bygge og gemme egne saet
- Unicode Braille-tilstand for hoj-taethedsoutput
- Blok-element-tilstand (halvblokke, kvadranter) for skarpere kanter

### Animationer og video
- Konvertering af GIF til animeret ASCII
- Videokonvertering frame-for-frame med eksport som mp4 eller animeret tekstfil
- Realtids-webkamera ASCII-preview

### Deling og integration
- Eksport til HTML med farvekodet CSS
- Kopiering til udklipsholder med et enkelt klik
- REST API-endpoint til integration med andre vaerktoejer
- CLI-interface til scripting og automatisering

### Tilgaengelighed og brugervenlighed
- Flersproget grænseflade
- Genvejstaster for alle primæere handlinger
- Historikoversigt over tidligere konverteringer
- Møerkt og lyst tema

---

## Installation

> Installationsvejledning kommer snart.

---

## Brug

> Dokumentation og eksempler kommer snart.

---

## Licens

[MIT](LICENSE)
