# Prosjekt 2 Statistikk og simulering 

## Uke 5

Forslag på arter og simulere

| Rovdyr | Byttedyr |
| ------ | -------- |
| Ulv    | Sau      |
| Rev    | Kanin    |
| Fugl   | Mark     |
| Hval   | Krill    |

### Hval:

Levetid: 80-90 år ~ 1080 månder

Spiser: 40 million krill per dag (3,6 tonn med krill) ~ 1.200 millioner krill pr måned, 108 tonn pr måned

Kjønnsmoden: 8-10 år ~ 120 månder

Forplantning: 1 barn hvert 2-3 år ~ 36 månder mellom forplanting

Drektig: 12 månder ~ 365 dager

Barne hval slutter å patte ved ca 7 månder.

### Krill:

I Norskehavet og Barentshavet er den årlige krillproduksjonen beregnet til ca. 287 millioner tonn.

Størrelsen på Norskehavet og Barentshavet kombinert ca 2,5 millioner km²

287 millioner tonn med krill pr år / 2,5 millioner km² = 115 tonn med krill pr km² pr år

Beveger seg lite. Kun opp og ned i vannlaget. Holder seg  samlet i store grupper 

Levetid: 2-6 år ~ 72 måneder.

## Agentenes egenskaper

### Hval: 

Beveger seg over store avstander.

Lever i flokk

Spiser masse

Formerer seg sakte

Må ha mat for å overleve

### Krill:

Beveger seg lite

Formerer seg fort

Ingen forsvarsmekanisme

Holder seg i flokk

Trenger ikke mat for å overleve, i simuleringen

## Simuleringsmiljø

Størrelsen på Barentshavet og Norskehavet er på 2,5 millioner km². Dette kan vi dele i seller på 1.000 km² som vil gi rutenet på 500x500 = 250000 seller.

I ytterkant av rutenettet kjører vi wraparound. Dette er ikke realistisk men gjør simuleringen mye lettere og implementere. 

Hvert steg representerer 1 måned IRL



### Sources

 - https://www.whalefacts.org/krill-facts/
 - [https://snl.no/blåhval](https://snl.no/blåhval)
 - https://snl.no/krill
 - https://no.wikipedia.org/wiki/Bl%C3%A5hval


# Implementasjon

The simulator must be able to produce a log file with comma-separated values (CVS) recording statistically data.
As a minimum, you should record

1. Logged to CSV file:
    - the age and time of death for every agent must be recorded.
    - the population sizes for each timestep.
2. Make some (not all) of the model features configurable, so that you can experiment with variations of the model (e.g. starting population size, grid size, etc.).
3. Visualise the location of individual agents at each time step. (The example from Barnes and Kolling tells you how 
to do this.)



