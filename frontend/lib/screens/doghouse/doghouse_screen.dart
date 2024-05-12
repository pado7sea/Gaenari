import 'package:flutter/material.dart';
import 'package:forsythia/screens/coin/coin_screen.dart';
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
      appBar: AppBar(
        title: Text('강아지집'),
      ),
      body: ElevatedButton(
          onPressed: () {
            Navigator.of(context).push(SlidePageRoute(nextPage: CoinScreen()));
          },
          child: Text('코인')),
    );
  }
}
