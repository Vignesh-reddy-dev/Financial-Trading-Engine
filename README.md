# Financial Trading Engine

A high-performance financial trading system built using a hybrid **B+ Tree + Segment Tree** data structure — mirroring how production exchanges like NSE/BSE store and query millions of timestamped trades.

## Data Structures Used
- **B+ Tree** — O(log N) indexing of trades by timestamp
- **Segment Tree** — O(log N) range analytics (sum, min, max over trade windows)

## Features
- Insert, update, delete, and range-query trades in O(log N)
- Range analytics run **40x faster** than naive array scans
- Benchmarked across 10,000+ synthetic trade records
- Full CRUD support with worst-case O(log N + log B) per operation

## Tech Stack
Java

## How to Run
1. Clone the repo
2. Open in Eclipse or any Java IDE
3. Run `Main.java`

## Real World Mapping
Mirrors database indexing engines like MySQL InnoDB and Oracle which use B+ Tree structures internally — and how stock exchanges like NSE/BSE log and retrieve trade data by timestamp.
