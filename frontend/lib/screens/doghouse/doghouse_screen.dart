import 'package:flutter/material.dart';
import 'package:forsythia/screens/coin/coin_screen.dart';
import 'package:forsythia/screens/inventory/inventory_screen.dart';
import 'package:forsythia/screens/item/item_screen.dart';
import 'package:forsythia/widgets/dog_app_bar.dart';
import 'package:forsythia/widgets/slide_page_route.dart';
import 'package:go_router/go_router.dart';

class DogHouseScreen extends StatefulWidget {
  const DogHouseScreen({super.key});

  @override
  State<DogHouseScreen> createState() => _DogHouseScreenState();
}

class _DogHouseScreenState extends State<DogHouseScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: DogAppBar(),
      body: Column(
        children: [
          ElevatedButton(
              onPressed: () {
                Navigator.of(context)
                    .push(SlidePageRoute(nextPage: CoinScreen()));
              },
              child: Text('코인')),
          ElevatedButton(
              onPressed: () {
                Navigator.of(context)
                    .push(SlidePageRoute(nextPage: ItemScreen()));
              },
              child: Text('뽑기')),
          ElevatedButton(
              onPressed: () {
                Navigator.of(context)
                    .push(SlidePageRoute(nextPage: InventoryScreen()));
              },
              child: Text('보관함')),
        ],
      ),
    );
  }
}
