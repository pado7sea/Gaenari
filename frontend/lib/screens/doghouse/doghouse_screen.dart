import 'package:flutter/material.dart';

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
    ));
  }
}
