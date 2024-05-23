import 'package:flutter/material.dart';

class SlidePageRoute extends PageRouteBuilder {
  final Widget nextPage;

  SlidePageRoute({required this.nextPage})
      : super(
          pageBuilder: (context, animation, secondaryAnimation) => nextPage,
          transitionsBuilder: (context, animation, secondaryAnimation, child) {
            const begin = Offset(1.0, 0.0);
            const end = Offset.zero;
            const curve = Curves.ease;
            var tween =
                Tween(begin: begin, end: end).chain(CurveTween(curve: curve));
            var offsetAnimation = animation.drive(tween);
            return SlideTransition(
              position: offsetAnimation,
              child: child,
            );
          },
        );
}


//아래처름 쓰면 됨미다

// onTap: () {
//   Navigator.of(context).push(
//   SlidePageRoute(nextPage: MatePage()));
// },